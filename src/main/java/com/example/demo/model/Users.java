package com.example.demo.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
    //Create class "Users" mapping table "users" in database
    @Entity
    @Table(name = "users")
    public class Users {

        @Id
        @Column(name="id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name="phone_number", unique = true, length = 15)
        private String phoneNumber;

        @Column(name="email", unique = true, nullable = false, length = 50)
        private String email;

        @Column(name="password_hash", nullable = false)
        private String passwordHash;

        @Column(name="full_name", length = 100)
        private String fullName;

        @CreationTimestamp
        @Column(name="create_at",nullable = false, updatable = false)
        private LocalDateTime createAt;
    }

