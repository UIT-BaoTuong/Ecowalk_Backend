package com.example.demo.service;

import com.example.demo.model.Runs;
import com.example.demo.model.Users;
import com.example.demo.repository.RunsRepository;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RunsRepository runsRepository;

    @Autowired
    private NotificationService notificationService; // Service gửi FCM

    // Hàm gọi xuống Database để cập nhật token (Giữ nguyên)
    public void updateFcmToken(Long userId, String token) {
        usersRepository.updateFcmToken(userId, token);
    }

    /**
     * Hàm này làm 3 việc:
     * 1. Lưu chuyến chạy mới vào DB.
     * 2. Kiểm tra xem có đạt mục tiêu 50km/100km không.
     * 3. Kiểm tra xem có thăng hạng không.
     */
    public void saveRunAndCheckNotifications(Runs newRun) {
        Long userId = newRun.getUserId();

        // Lấy thông tin User để lấy Token gửi thông báo
        Users user = usersRepository.findById(userId).orElse(null);
        if (user == null) return; // Không có user thì thôi

        // --- BƯỚC 1: LẤY SỐ LIỆU "TRƯỚC KHI LƯU" ---
        
        // Mốc thời gian: 0h00 ngày Thứ 2 của tuần này (để tính Goal tuần)
        LocalDateTime startOfWeek = LocalDateTime.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        // Lấy hạng hiện tại (Nếu chưa có thì cho là hạng rất thấp: 999999)
        Integer oldRank = runsRepository.getUserRank(userId, startOfWeek);
        if (oldRank == null) oldRank = 999999;

        // Lấy tổng quãng đường từ đầu tuần đến giờ
        Double currentWeekDistance = runsRepository.getTotalDistanceFrom(userId, startOfWeek);
        if (currentWeekDistance == null) currentWeekDistance = 0.0;

        // --- BƯỚC 2: LƯU CHUYẾN CHẠY VÀO DB ---
        runsRepository.save(newRun);

        // --- BƯỚC 3: KIỂM TRA LOGIC THÔNG BÁO ---

        // Tính lại tổng mới
        Double newWeekDistance = currentWeekDistance + newRun.getDistanceKm();
        
        // Tính lại hạng mới
        Integer newRank = runsRepository.getUserRank(userId, startOfWeek);
        if (newRank == null) newRank = oldRank;

        String fcmToken = user.getFcmToken();
        if (fcmToken == null || fcmToken.isEmpty()) return; // Không có token thì khỏi gửi

        // === LOGIC 1: CHECK MỤC TIÊU TUẦN (50km, 100km) ===
        
        // Nếu trước đó < 50 và giờ >= 50
        if (currentWeekDistance < 50 && newWeekDistance >= 50) {
            notificationService.sendToToken(fcmToken,
                    "🏆 CÁN MỐC 50KM!",
                    "Chúc mừng! Bạn đã chinh phục 50km đầu tiên trong tuần này. Cố lên!");
        }

        // Nếu trước đó < 100 và giờ >= 100
        if (currentWeekDistance < 100 && newWeekDistance >= 100) {
            notificationService.sendToToken(fcmToken,
                    "🥇 CHIẾN THẦN 100KM!",
                    "Không thể tin nổi! Bạn đã chạy 100km trong tuần. Bạn là huyền thoại!");
        }

        // === LOGIC 2: CHECK THĂNG HẠNG ===
        
        // Hạng nhỏ hơn nghĩa là cao hơn (Ví dụ: 5 -> 4 là Thăng hạng)
        if (newRank < oldRank) {
            notificationService.sendToToken(fcmToken,
                    "🚀 THĂNG HẠNG!",
                    "Bạn vừa vượt lên hạng " + newRank + " trên bảng xếp hạng tuần. Giữ vững phong độ nhé!");
        }
    }
}