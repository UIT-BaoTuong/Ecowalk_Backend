package com.example.demo.config; // Đổi lại đúng package của bạn

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FirebaseConfig {

  @Bean
  public FirebaseMessaging firebaseMessaging() throws IOException {
    // Đọc file service-account.json từ thư mục resources
    ClassPathResource resource = new ClassPathResource("service-account.json");

    // Kiểm tra xem file có tồn tại không
    if (!resource.exists()) {
      throw new IOException("Không tìm thấy file service-account.json trong thư mục resources!");
    }

    InputStream inputStream = resource.getInputStream();

    FirebaseOptions options =
        FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(inputStream)).build();

    // Chỉ khởi tạo nếu chưa có App nào chạy (để tránh lỗi init 2 lần)
    if (FirebaseApp.getApps().isEmpty()) {
      FirebaseApp.initializeApp(options);
    }

    return FirebaseMessaging.getInstance();
  }
}
