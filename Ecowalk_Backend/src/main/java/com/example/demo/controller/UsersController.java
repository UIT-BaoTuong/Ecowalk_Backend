package com.example.demo.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
@RestController
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;
    @GetMapping("/api/users")
    private List<Users> getAllUsers()
    {
        return usersRepository.findAll();
    }
    @PostMapping("/api/user/by_email")
    private Users findUserByEmail(@RequestBody Map<String,String> body)
    {
        String email = body.get("email");
        return usersRepository.findByEmail(email);
    }
    @PostMapping("/api/user/by_phone_number")
    private Users findUserByPhone(@RequestBody Map<String,String> body)
    {
        String phoneNumber = body.get("phoneNumber");
        return usersRepository.findByPhoneNumber(phoneNumber);
    }
    @PostMapping("/api/exists_user/by_email")
    private Boolean existsUserByEmail(@RequestBody Map<String,String> body)
    {
        String email = body.get("email");
        return usersRepository.existsByEmail(email);
    }
    @PostMapping("/api/exists_user/by_phone_number")
    private Boolean existsUserByPhoneNumber(@RequestBody Map<String,String> body)
    {
        String phoneNumber = body.get("phoneNumber");
        return usersRepository.existsByPhoneNumber(phoneNumber);
    }
}