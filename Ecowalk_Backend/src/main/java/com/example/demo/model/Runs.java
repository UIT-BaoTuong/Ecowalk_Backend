package com.example.demo.model;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
@Entity
@Table(name = "runs")
public class Runs {
    @Id
    @Column(name = "run_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long runId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "distance_km", nullable = false)
    private Double distanceKm;
    
    @Lob
    @Column(name = "coordinates_json")
    private String coordinatesJson;

    @Column(name = "user_id", nullable = false)
    private Long UserId;
}
