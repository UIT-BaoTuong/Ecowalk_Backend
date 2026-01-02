package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired private JavaMailSender mailSender;

  public void sendResetOtp(String to, String otp) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject("Reset password OTP from EcoWalk");
    message.setText(
        """
            Xin chào,

            Mã OTP của bạn là: %s
            Mã này có hiệu lực trong 5 phút.

            Nếu bạn không yêu cầu đặt lại mật khẩu, hãy bỏ qua email này.

            Trân trọng,
            Đội ngũ EcoWalk
            """
            .formatted(otp));
    mailSender.send(message);
  }
}
