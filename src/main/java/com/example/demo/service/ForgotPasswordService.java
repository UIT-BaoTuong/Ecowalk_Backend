package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import jakarta.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ForgotPasswordService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // Sinh OTP an toàn 6 chữ số
    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    // Send OTP to email
    public void sendOtpToEmail(String email) {
        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Email không tồn tại trong hệ thống.");
        }

        String otp = generateOtp();
        user.setResetToken(otp);
        user.setResetTokenExpire(LocalDateTime.now().plusMinutes(5)); // hết hạn sau 5 phút
        usersRepository.save(user);

        // Send OTP via email
        emailService.sendResetOtp(email, otp);
    }

    // Check OTP and reset password
    public void resetPasswordWithOtp(String email, String otp, String newPassword) {
        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Email is not registered.");
        }

        if (user.getResetToken() == null || !user.getResetToken().equals(otp)) {
            throw new RuntimeException("Invalid OTP.");
        }

        if (user.getResetTokenExpire() == null || user.getResetTokenExpire().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired. Please request a new one.");
        }

        // Update password (hash)
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpire(null);

        usersRepository.save(user);
    }
}
