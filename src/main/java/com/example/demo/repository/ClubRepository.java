package com.example.demo.repository;

import com.example.demo.model.Club;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // 🔽 [THÊM]
    @Query("""
        SELECT c FROM Club c
        WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Club> searchAll(@Param("keyword") String keyword);

    // 🔽 [THÊM] – CLB user đã tham gia
    @Query("""
        SELECT cm.club FROM ClubMember cm
        WHERE cm.user.id = :userId
        AND (
            LOWER(cm.club.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(cm.club.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    List<Club> searchMyClubs(
            @Param("userId") Long userId,
            @Param("keyword") String keyword
    );

    // 🔽 [THÊM] – CLB gợi ý
    @Query("""
        SELECT c FROM Club c
        WHERE c.id NOT IN (
            SELECT cm.club.id FROM ClubMember cm WHERE cm.user.id = :userId
        )
        AND (
            LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    List<Club> searchSuggestedClubs(
            @Param("userId") Long userId,
            @Param("keyword") String keyword
    );
}