package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rewards")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;        // Tên quà (VD: Voucher 50k)

    private String description; // Mô tả

    @Column(nullable = false)
    private int cost;           // Số điểm cần để đổi

    @Column(columnDefinition = "TEXT")
    private String image_url;    
}