package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.NotificationService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UsersController {

  @Autowired private NotificationService notificationService;

  @Autowired private UsersRepository usersRepository;

  @Autowired private CloudinaryService cloudinaryService;

  @Autowired private com.example.demo.service.UsersService usersService;

  @GetMapping("/api/users")
  private List<Users> getAllUsers() {
    return usersRepository.findAll();
  }

  @PostMapping("/api/users/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String password = body.get("password");

    Users user = usersRepository.findByEmail(email);

    if (user != null && user.getPasswordHash().equals(password)) {
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.badRequest().body("Sai email hoặc mật khẩu");
  }

  @PostMapping("/api/user/by_email")
  private Users findUserByEmail(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    return usersRepository.findByEmail(email);
  }

  @PostMapping("/api/user/by_phone_number")
  private Users findUserByPhone(@RequestBody Map<String, String> body) {
    String phoneNumber = body.get("phoneNumber");
    return usersRepository.findByPhoneNumber(phoneNumber);
  }

  @PostMapping("/api/exists_user/by_email")
  private Boolean existsUserByEmail(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    return usersRepository.existsByEmail(email);
  }

  @PostMapping("/api/exists_user/by_phone_number")
  private Boolean existsUserByPhoneNumber(@RequestBody Map<String, String> body) {
    String phoneNumber = body.get("phoneNumber");
    return usersRepository.existsByPhoneNumber(phoneNumber);
  }

  @PostMapping("/api/users/{id}/avatar")
  public ResponseEntity<?> updateAvatar(
      @PathVariable Long id, @RequestParam("file") MultipartFile file) {
    try {
      Users user = usersRepository.findById(id).orElse(null);
      if (user == null) {
        return ResponseEntity.badRequest().body("User không tồn tại");
      }

      if (file.isEmpty()) {
        return ResponseEntity.badRequest().body("File ảnh trống!");
      }

      String imageUrl = cloudinaryService.uploadImage(file);

      if (imageUrl == null) {
        return ResponseEntity.badRequest().body("Lỗi khi upload lên Cloudinary");
      }

      user.setAvatarUrl(imageUrl);
      usersRepository.save(user);

      return ResponseEntity.ok("Cập nhật Avatar thành công! Link: " + imageUrl);

    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
    }
  }

  @PostMapping("/api/users/{id}/update_name")
  public ResponseEntity<?> updateName(
      @PathVariable Long id, @RequestBody Map<String, String> body) {
    Users user = usersRepository.findById(id).orElse(null);
    if (user == null) return ResponseEntity.badRequest().body("User not found");

    String newName = body.get("fullName");
    if (newName != null && !newName.isEmpty()) {
      user.setFullName(newName);
      usersRepository.save(user);
      return ResponseEntity.ok("Updated name success");
    }
    return ResponseEntity.badRequest().body("Name is empty");
  }

  @PostMapping("/api/users/fcm-token")
  public ResponseEntity<?> saveFcmToken(@RequestParam Long userId, @RequestParam String token) {
    try {
      // Gọi service để lưu vào DB (Bạn cần viết hàm này bên Service nhé)
      usersService.updateFcmToken(userId, token);
      return ResponseEntity.ok("Đã lưu Token thành công!");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Lỗi lưu token: " + e.getMessage());
    }
  }

  @GetMapping("/api/test-notif")
  public ResponseEntity<?> testNotification(@RequestParam String token) {
    notificationService.sendToToken(
        token,
        "Test từ Backend",
        "Chúc mừng! Server Spring Boot đã kết nối thành công với App Android.");
    return ResponseEntity.ok("Đã gửi lệnh tin nhắn!");
  }
}
