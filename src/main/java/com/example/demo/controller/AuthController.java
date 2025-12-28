package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.JwtService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
  @Autowired private UsersRepository usersRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String password = body.get("password");

    Users user = usersRepository.findByEmail(email);
    if (user == null) {
      return ResponseEntity.badRequest().body(Map.of("error", "Email không tồn tại"));
    }

    // So sánh password mã hóa
    if (!passwordEncoder.matches(password, user.getPasswordHash())) {
      return ResponseEntity.badRequest().body(Map.of("error", "Sai mật khẩu"));
    }
    // Sinh token JWT
    String accessToken = jwtService.generateAccessToken(email);
    String refreshToken = jwtService.generateRefreshToken(email);

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Đăng nhập thành công");
    response.put("access_token", accessToken);
    response.put("refresh_token", refreshToken);
    response.put("user_id", user.getId()); // Thêm user_id vào response

    return ResponseEntity.ok(response);
  }

  @PostMapping("/register")
  private ResponseEntity<?> registerUser(@RequestBody Map<String, String> body) {
    String fullName = body.get("full_name");
    String email = body.get("email");
    String phoneNumber = body.get("phone_number");
    String password = body.get("password");
    // Check user is exist or not
    if (usersRepository.existsByEmail(email) == true) {
      return ResponseEntity.badRequest().body("Email alredy exists");
    }
    if (usersRepository.existsByPhoneNumber(phoneNumber) == true) {
      return ResponseEntity.badRequest().body("Phone number alredy exists");
    }
    // Create class user and set value
    Users newUser = new Users();

    newUser.setFullName(fullName);
    newUser.setEmail(email);
    newUser.setPhoneNumber(phoneNumber);
    newUser.setPasswordHash(passwordEncoder.encode(password));
    newUser.setCreateAt(LocalDateTime.now());

    // Create a new user in database "users"
    usersRepository.save(newUser);
    // Return notification
    return ResponseEntity.ok("User registered successfully");
  }

  // API refresh token (khi access token hết hạn)
  @PostMapping("/refresh")
  public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
    String refreshToken = body.get("refresh_token");
    String email = jwtService.validateToken(refreshToken);
    if (email == null) {
      return ResponseEntity.badRequest()
          .body(Map.of("error", "Refresh token không hợp lệ hoặc hết hạn"));
    }

    // Tạo token mới
    String newAccessToken = jwtService.generateAccessToken(email);
    return ResponseEntity.ok(Map.of("access_token", newAccessToken));
  }
}
