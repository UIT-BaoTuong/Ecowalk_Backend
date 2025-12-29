package com.example.demo.repository;

import com.example.demo.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRespository extends JpaRepository<Club, Long> {
}
