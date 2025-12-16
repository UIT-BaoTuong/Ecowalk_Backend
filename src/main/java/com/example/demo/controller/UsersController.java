package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Create API
@RestController
public class UsersController {

  @Autowired private UsersRepository usersRepository;

  // Get all users data
  @GetMapping("/api/users")
  private List<Users> getAllUsers() {
    return usersRepository.findAll();
  }

  // Find user by email
  @PostMapping("/api/user/by_email")
  private Users findUserByEmail(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    return usersRepository.findByEmail(email);
  }

  // Find user by phone_number
  @PostMapping("/api/user/by_phone_number")
  private Users findUserByPhone(@RequestBody Map<String, String> body) {
    String phoneNumber = body.get("phoneNumber");
    return usersRepository.findByPhoneNumber(phoneNumber);
  }

  // Check exists user by email, if exists return true else false
  @PostMapping("/api/exists_user/by_email")
  private Boolean existsUserByEmail(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    return usersRepository.existsByEmail(email);
  }

  // Check exists user by phone number, if exist return true else false
  @PostMapping("/api/exists_user/by_phone_number")
  private Boolean existsUserByPhoneNumber(@RequestBody Map<String, String> body) {
    String phoneNumber = body.get("phoneNumber");
    return usersRepository.existsByPhoneNumber(phoneNumber);
  }
}
