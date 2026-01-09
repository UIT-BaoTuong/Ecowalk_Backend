package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RunActivityResponse {
    private Long runId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double distanceKm;
    private Long userId;
    private String userName;
    private String avatarUrl;
    private String mapData;
    private Double averageSpeed; // km/h
    private Double calories;
}