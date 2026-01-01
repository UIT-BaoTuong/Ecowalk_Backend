package com.example.demo.repository;

import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Declaration SQL query methods by using JpaRepository
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
  Users findByEmail(String email);

  Users findByPhoneNumber(String phoneNumber);

  boolean existsByEmail(String email);

  boolean existsByPhoneNumber(String phoneNumber);

  Users findByResetToken(String token);

  @org.springframework.data.jpa.repository.Modifying
  @org.springframework.transaction.annotation.Transactional
  @org.springframework.data.jpa.repository.Query(
      "UPDATE Users u SET u.fcmToken = :token WHERE u.id = :userId")
  void updateFcmToken(
      @org.springframework.data.repository.query.Param("userId") Long userId,
      @org.springframework.data.repository.query.Param("token") String token);
}
