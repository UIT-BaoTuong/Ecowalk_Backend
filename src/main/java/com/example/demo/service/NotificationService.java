package com.example.demo.service;

import com.example.demo.repository.NotificationRepository;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  @Autowired private NotificationRepository notificationRepository;

  // Hàm cơ bản để bắn tin sang Firebase VÀ lưu vào Database
  public void sendToToken(String targetToken, String title, String body) {
    sendToToken(targetToken, title, body, null, null);
  }

  // Hàm mở rộng: gửi Firebase + lưu DB + lưu type
  public void sendToToken(String targetToken, String title, String body, Long userId, String type) {
    if (targetToken == null || targetToken.isEmpty()) return;

    try {
      String channelId = "ecowalk_channel";

      AndroidConfig androidConfig =
          AndroidConfig.builder()
              .setNotification(AndroidNotification.builder().setChannelId(channelId).build())
              .build();

      Message message =
          Message.builder()
              .setToken(targetToken)
              .setNotification(Notification.builder().setTitle(title).setBody(body).build())
              .setAndroidConfig(androidConfig)
              .build();

      FirebaseMessaging.getInstance().send(message);
      System.out.println("✅ Sent notification to: " + targetToken);

      // LƯU VÀO DATABASE nếu có userId
      if (userId != null) {
        saveNotificationToDb(userId, title, body, type);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Hàm lưu thông báo vào database
  public void saveNotificationToDb(Long userId, String title, String body, String type) {
    try {
      com.example.demo.model.Notification notification =
          com.example.demo.model.Notification.builder()
              .userId(userId)
              .title(title)
              .body(body)
              .type(type)
              .isRead(false)
              .build();

      notificationRepository.save(notification);
      System.out.println("💾 Saved notification to DB for user: " + userId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // --- LOGIC 1: CRON JOB NHẮC CHẠY (Chạy 8h sáng mỗi ngày) ---
  // Cần thêm @EnableScheduling vào file EcoWalkApplication.java
  @Scheduled(cron = "0 0 8 * * ?")
  public void remindInactiveUsers() {
    System.out.println("Checking for inactive users...");
  }
}
