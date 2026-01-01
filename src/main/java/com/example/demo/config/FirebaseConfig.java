package com.example.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FirebaseConfig {

  @Value("${firebase.credentials.path:service-account.json}")
  private String credentialsPath;

  @Bean
  public FirebaseMessaging firebaseMessaging() throws IOException {
    ClassPathResource resource = new ClassPathResource(credentialsPath);

    if (!resource.exists()) {
      System.err.println("⚠️  CẢNH BÁO: Không tìm thấy file " + credentialsPath);
      System.err.println("Firebase notifications sẽ không hoạt động nếu file này không tồn tại!");
      System.err.println("Hướng dẫn: Đặt file service-account.json trong src/main/resources/");
      throw new IOException("Không tìm thấy file " + credentialsPath + " trong thư mục resources!");
    }

    InputStream inputStream = resource.getInputStream();
    FirebaseOptions options =
        FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(inputStream)).build();

    if (FirebaseApp.getApps().isEmpty()) {
      FirebaseApp.initializeApp(options);
    }

    return FirebaseMessaging.getInstance();
  }
}
