package com.example.demo.controller;

import com.example.demo.model.Reward;
import com.example.demo.model.Users;
import com.example.demo.repository.RewardRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.NotificationService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

  @Autowired private RewardRepository rewardRepository;

  @Autowired private UsersRepository usersRepository;

  @Autowired private NotificationService notificationService;

  @GetMapping
  public ResponseEntity<List<Reward>> getAllRewards() {
    return ResponseEntity.ok(rewardRepository.findAll());
  }

  @PostMapping
  public ResponseEntity<Reward> createReward(@RequestBody Reward reward) {
    return ResponseEntity.ok(rewardRepository.save(reward));
  }

  @PostMapping("/redeem")
  public ResponseEntity<?> redeemReward(@RequestBody Map<String, Long> payload) {
    try {
      Long userId = payload.get("userId");
      Long rewardId = payload.get("rewardId");

      if (userId == null || rewardId == null) {
        return ResponseEntity.badRequest().body("Thiếu userId hoặc rewardId");
      }

      Optional<Users> userOpt = usersRepository.findById(userId);
      Optional<Reward> rewardOpt = rewardRepository.findById(rewardId);

      if (userOpt.isEmpty()) {
        return ResponseEntity.badRequest().body("User not found (ID: " + userId + ")");
      }
      if (rewardOpt.isEmpty()) {
        return ResponseEntity.badRequest().body("Reward not found (ID: " + rewardId + ")");
      }

      Users user = userOpt.get();
      Reward reward = rewardOpt.get();

      int currentPoints = user.getCurrentPoints();
      int cost = reward.getCost();

      if (currentPoints < cost) {
        return ResponseEntity.badRequest()
            .body("Không đủ điểm! Bạn có: " + currentPoints + ", Cần: " + cost);
      }

      user.setCurrentPoints(currentPoints - cost);
      usersRepository.save(user);

      // 🎁 LƯU THÔNG BÁO VÀO DATABASE
      String fcmToken = user.getFcmToken();
      if (fcmToken != null && !fcmToken.isEmpty()) {
        notificationService.saveNotificationToDb(
            userId,
            "🎁 ĐỔI QUÀ THÀNH CÔNG!",
            "Bạn đã đổi quà: " + reward.getName(),
            "reward_redeemed");
      }

      return ResponseEntity.ok("Đổi quà thành công! Số điểm còn lại: " + user.getCurrentPoints());

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().body("Lỗi server: " + e.getMessage());
    }
  }
}
