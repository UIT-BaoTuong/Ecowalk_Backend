package com.example.demo.controller;

import com.example.demo.model.Notification;
import com.example.demo.repository.NotificationRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

  @Autowired private NotificationRepository notificationRepository;

  // Lấy danh sách thông báo của user
  @GetMapping("/{userId}")
  public ResponseEntity<?> getUserNotifications(@PathVariable Long userId) {
    try {
      List<Notification> notifications =
          notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
      return ResponseEntity.ok(
          Map.of(
              "notifications", notifications,
              "total", notifications.size(),
              "unread", notificationRepository.countByUserIdAndIsReadFalse(userId)));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Lỗi: " + e.getMessage());
    }
  }

  // Lấy số lượng thông báo chưa đọc
  @GetMapping("/{userId}/unread-count")
  public ResponseEntity<?> getUnreadCount(@PathVariable Long userId) {
    try {
      long unreadCount = notificationRepository.countByUserIdAndIsReadFalse(userId);
      return ResponseEntity.ok(Map.of("unreadCount", unreadCount));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Lỗi: " + e.getMessage());
    }
  }

  // Đánh dấu 1 thông báo là đã đọc
  @PutMapping("/{notificationId}/mark-read")
  public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) {
    try {
      notificationRepository.markAsRead(notificationId);
      return ResponseEntity.ok("Đánh dấu là đã đọc");
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Lỗi: " + e.getMessage());
    }
  }

  // Đánh dấu TẤT CẢ thông báo của user là đã đọc
  @PutMapping("/{userId}/mark-all-read")
  public ResponseEntity<?> markAllAsRead(@PathVariable Long userId) {
    try {
      notificationRepository.markAllAsReadByUser(userId);
      return ResponseEntity.ok("Đánh dấu tất cả là đã đọc");
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Lỗi: " + e.getMessage());
    }
  }

  // Xóa thông báo cũ hơn 30 ngày
  @DeleteMapping("/{userId}/cleanup")
  public ResponseEntity<?> cleanupOldNotifications(@PathVariable Long userId) {
    try {
      notificationRepository.deleteOldNotifications(userId);
      return ResponseEntity.ok("Xóa thông báo cũ thành công");
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Lỗi: " + e.getMessage());
    }
  }
}
