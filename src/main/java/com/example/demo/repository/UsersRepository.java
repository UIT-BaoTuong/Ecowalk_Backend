package com.example.demo.repository;
import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//Declaration SQL query methods by using JpaRepository
@Repository
public interface UsersRepository extends JpaRepository <Users, Long>{
    Users findByEmail(String email);
    Users findByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}