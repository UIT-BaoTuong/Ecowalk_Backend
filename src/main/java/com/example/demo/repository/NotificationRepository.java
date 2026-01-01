package com.example.demo.repository;

import com.example.demo.model.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

  // Lấy danh sách thông báo của user, sắp xếp từ mới nhất đến cũ nhất
  List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

  // Lấy số lượng thông báo chưa đọc của user
  long countByUserIdAndIsReadFalse(Long userId);

  // Đánh dấu 1 thông báo là đã đọc
  @Modifying
  @Transactional
  @Query("UPDATE Notification n SET n.isRead = true WHERE n.id = :notificationId")
  void markAsRead(@Param("notificationId") Long notificationId);

  // Đánh dấu tất cả thông báo của user là đã đọc
  @Modifying
  @Transactional
  @Query("UPDATE Notification n SET n.isRead = true WHERE n.userId = :userId")
  void markAllAsReadByUser(@Param("userId") Long userId);

  // Xóa thông báo cũ hơn 30 ngày
  @Modifying
  @Transactional
  @Query(
      value = "DELETE FROM notifications WHERE user_id = :userId AND created_at < NOW() - INTERVAL '30 days'",
      nativeQuery = true)
  void deleteOldNotifications(@Param("userId") Long userId);
}
