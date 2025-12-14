package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "runs")
// XÓA DÒNG NÀY: @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Getter
@Setter
public class Runs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "run_id")
    private Long runId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "distance_km", nullable = false)
    private Double distanceKm;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}