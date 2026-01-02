package com.example.demo.controller;

import com.example.demo.service.ForgotPasswordService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ForgotPasswordController {

<<<<<<< HEAD
  @Autowired private ForgotPasswordService forgotPasswordService;

  // send OTP to email
  @PostMapping("/forgot-password")
  public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
    try {
      String email = request.get("email");
      forgotPasswordService.sendOtpToEmail(email);
      return ResponseEntity.ok(Map.of("message", "OTP sent to email!"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  // Accept OTP and reset password
  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
    try {
      String email = request.get("email");
      String otp = request.get("otp");
      String newPassword = request.get("newPassword");
      forgotPasswordService.resetPasswordWithOtp(email, otp, newPassword);
      return ResponseEntity.ok(Map.of("message", "Successfully reset password."));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }
=======
    @Autowired
    private ForgotPasswordService forgotPasswordService;

    // send OTP to email
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            forgotPasswordService.sendOtpToEmail(email);
            return ResponseEntity.ok(Map.of("message", "OTP sent to email!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Accept OTP and reset password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String otp = request.get("otp");
            String newPassword = request.get("newPassword");
            forgotPasswordService.resetPasswordWithOtp(email, otp, newPassword);
            return ResponseEntity.ok(Map.of("message", "Successfully reset password."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
>>>>>>> main
}
