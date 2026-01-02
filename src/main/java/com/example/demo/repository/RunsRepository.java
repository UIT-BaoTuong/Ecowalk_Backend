package com.example.demo.repository;

import com.example.demo.model.RankingDTO;
import com.example.demo.model.Runs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RunsRepository extends JpaRepository<Runs, Long> {

    List<Runs> findByUserId(Long userId);

    @Query("SELECT u.id as userId, u.fullName as fullName, u.avatarUrl as avatarUrl, " +
           "COALESCE(SUM(r.distanceKm), 0) as totalDistance " +
           "FROM Users u LEFT JOIN Runs r ON u.id = r.userId AND (r.startTime >= :startDate) " +
           "GROUP BY u.id, u.fullName, u.avatarUrl " +
           "ORDER BY totalDistance DESC, u.fullName ASC")
    List<RankingDTO> getTopRunners(@Param("startDate") LocalDateTime startDate);

    @Query(value = "SELECT CAST(rank_val AS INTEGER) FROM (" +
           "  SELECT u.id, RANK() OVER (ORDER BY COALESCE(SUM(r.distance_km), 0) DESC, u.full_name ASC) as rank_val " +
           "  FROM users u LEFT JOIN runs r ON u.id = r.user_id AND (r.start_time >= :startDate) " +
           "  GROUP BY u.id, u.full_name" +
           ") as ranking_table WHERE id = :userId", nativeQuery = true)
    Integer getUserRank(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate);

    @Query(value = "SELECT COALESCE(SUM(distance_km), 0) FROM runs WHERE user_id = :userId AND CAST(start_time AS DATE) = CURRENT_DATE", nativeQuery = true)
    Double getTodayDistance(@Param("userId") Long userId);

    @Query(value = "SELECT CAST(COALESCE(SUM(EXTRACT(EPOCH FROM (end_time - start_time))), 0) AS INTEGER) FROM runs WHERE user_id = :userId AND CAST(start_time AS DATE) = CURRENT_DATE", nativeQuery = true)
    Integer getTodayTimeSeconds(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM runs WHERE user_id = :userId AND start_time >= CURRENT_DATE - INTERVAL '7 days'", nativeQuery = true)
    List<Runs> getLast7DaysRuns(@Param("userId") Long userId);

    @Query(value = "SELECT DISTINCT TO_CHAR(start_time, 'YYYY-MM-DD') FROM runs WHERE user_id = :userId", nativeQuery = true)
    List<String> getAllActiveDates(@Param("userId") Long userId);

    @Query(value = "SELECT TO_CHAR(start_time, 'YYYY-MM-DD'), COALESCE(SUM(distance_km), 0) FROM runs WHERE user_id = :userId GROUP BY TO_CHAR(start_time, 'YYYY-MM-DD')", nativeQuery = true)
       List<Object[]> getCalendarStats(@Param("userId") Long userId);

    @Query(value = "SELECT COALESCE(SUM(distance_km), 0) FROM runs WHERE user_id = :userId AND start_time >= :startDate", nativeQuery = true)
    Double getTotalDistanceFrom(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate);
}
