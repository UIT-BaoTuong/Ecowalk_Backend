package com.example.demo.controller;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;

@RestController
public class AuthController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/api/login")
    private void Login()
    {
        
    }
    @PostMapping("/api/register")
    private ResponseEntity<?> registerUser(@RequestBody Map<String,String> body)
    {
        String fullName = body.get("full_name");
        String email = body.get("email");
        String phoneNumber = body.get("phone_number");
        String password = body.get("password");
        //Check user is exist or not
        if(usersRepository.existsByEmail(email)==true)
        {
            return ResponseEntity.badRequest().body("Email alredy exists");
        }
        if(usersRepository.existsByPhoneNumber(phoneNumber)==true)
        {
            return ResponseEntity.badRequest().body("Phone number alredy exists");
        }
        //Create class user and set value
        Users newUser = new Users();

        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setPasswordHash(passwordEncoder.encode(password));
        newUser.setCreateAt(LocalDateTime.now());

        //Create a new user in database "users"
        usersRepository.save(newUser);
        //Return notification
        return ResponseEntity.ok("User registered successfully");
    }
}
