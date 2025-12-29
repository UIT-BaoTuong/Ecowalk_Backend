package com.example.demo.repository;

import com.example.demo.model.Club;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClubRepository extends JpaRepository<Club, Long> {

    // 🔽 CLB user đã tham gia
    @Query("""
        SELECT cm.club
        FROM ClubMember cm
        WHERE cm.user.id = :userId
    """)
    List<Club> findClubsUserJoined(Long userId);

    // 🔽 CLB user CHƯA tham gia
    @Query("""
        SELECT c
        FROM Club c
        WHERE c.id NOT IN (
            SELECT cm.club.id
            FROM ClubMember cm
            WHERE cm.user.id = :userId
        )
    """)
    List<Club> findSuggestedClubs(Long userId);
}