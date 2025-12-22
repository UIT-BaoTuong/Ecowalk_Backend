package com.example.demo.repository;

import com.example.demo.model.Runs;
import com.example.demo.model.RankingDTO;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RunsRepository extends JpaRepository<Runs, Long> {
    List<Runs> findByUserId(Long userId);

    @Query(value = "SELECT u.id as userId, u.fullName as fullName, u.avatarUrl as avatarUrl, " +
           "COALESCE(SUM(r.distanceKm), 0) as totalDistance " +
           "FROM Users u LEFT JOIN Runs r ON u.id = r.userId AND (r.startTime >= :startDate) " +
           "GROUP BY u.id, u.fullName, u.avatarUrl " +
           "ORDER BY totalDistance DESC, u.fullName ASC")
    List<RankingDTO> getTopRunners(@Param("startDate") LocalDateTime startDate);

    @Query(value = "SELECT rank_val FROM (" +
           "  SELECT u.id, RANK() OVER (ORDER BY COALESCE(SUM(r.distance_km), 0) DESC, u.full_name ASC) as rank_val " +
           "  FROM users u LEFT JOIN runs r ON u.id = r.user_id AND (r.start_time >= :startDate) " +
           "  GROUP BY u.id" +
           ") as ranking_table WHERE id = :userId", nativeQuery = true)
    Integer getUserRank(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate);
}