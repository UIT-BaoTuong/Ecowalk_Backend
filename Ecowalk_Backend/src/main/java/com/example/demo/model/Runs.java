package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
// Import 'Type' vẫn được giữ lại
import org.hibernate.annotations.Type; 
// XÓA DÒNG NÀY: import org.hibernate.annotations.TypeDef; 
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import java.time.LocalDateTime;
import java.util.List;

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

    // --- ĐÂY LÀ PHẦN ĐÃ SỬA ---
    // Thay thế @Type(type = "jsonb") bằng cú pháp mới
    @Type(JsonBinaryType.class) 
    @Column(name = "coordinates_json", columnDefinition = "jsonb")
    private List<Coordinate> coordinatesJson;
    // ----------------------------

    @Column(name = "user_id", nullable = false)
    private Long userId;
}