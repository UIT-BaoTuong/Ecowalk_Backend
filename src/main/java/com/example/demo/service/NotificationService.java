package com.example.demo.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.RewardRepository;
import com.example.demo.repository.RunPointsRepository;
import com.example.demo.repository.RunsRepository;

@Service
public class NotificationService {

    // Hàm cơ bản để bắn tin sang Firebase
    public void sendToToken(String targetToken, String title, String body) {
        if (targetToken == null || targetToken.isEmpty()) return;

        try {
            Message message = Message.builder()
                .setToken(targetToken)
                .setNotification(Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build())
                .build();
            
            FirebaseMessaging.getInstance().send(message);
            System.out.println("Sent notification to: " + targetToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- LOGIC 1: CRON JOB NHẮC CHẠY (Chạy 8h sáng mỗi ngày) ---
    // Cần thêm @EnableScheduling vào file EcoWalkApplication.java
    @Scheduled(cron = "0 0 8 * * ?") 
    public void remindInactiveUsers() {
        // Giả sử bạn viết hàm findInactiveUsers trong Repo trả về List user
        // List<User> users = userRepository.findInactiveUsers(3); // 3 ngày
        
        // for (User u : users) {
        //     sendToToken(u.getFcmToken(), "Nhắc nhở nhẹ!", "3 ngày rồi chưa chạy, xách giày lên nào!");
        // }
        System.out.println("Checking for inactive users...");
    }
}
