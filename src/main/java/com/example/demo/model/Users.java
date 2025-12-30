package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "phone_number", unique = true, length = 15)
  private String phoneNumber;

  @Column(name = "email", unique = true, nullable = false, length = 50)
  private String email;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  @Column(name = "full_name", length = 100)
  private String fullName;

  @CreationTimestamp
  @Column(name = "create_at", nullable = false, updatable = false)
  private LocalDateTime createAt;

  @Builder.Default
  @Column(name = "current_points", nullable = false, columnDefinition = "integer default 0")
  private int currentPoints = 0;

  @Column(name = "avatar_url")
  private String avatarUrl;

  @Column(name = "reset_token", length = 6)
    private String resetToken;  // save OTP token here

  @Column(name = "reset_token_expire")
  private LocalDateTime resetTokenExpire;  // OTP expiration time

  @Column(name = "fcm_token")
    private String fcmToken; // Biến lưu mã định danh gửi thông báo

    // Getter và Setter (Bắt buộc phải có)
    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
