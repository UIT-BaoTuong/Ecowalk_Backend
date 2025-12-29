package com.example.demo.controller;

import com.example.demo.model.Club;
import com.example.demo.dto.ClubResponse;

import com.example.demo.service.ClubQueryService; // 🔽 [THÊM]
import com.example.demo.service.ClubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;
    // 🔽 [THÊM]
    private final ClubQueryService clubQueryService;

    public ClubController(ClubService clubService, ClubQueryService clubQueryService) {
        this.clubService = clubService;
        this.clubQueryService = clubQueryService; // 🔽 [THÊM]
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createClub(
            @RequestParam Long userId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String sportType,
            @RequestParam String organizationType,
            @RequestParam(required = false) MultipartFile avatar
    ) {
        try {
            Club club = clubService.createClub(
                    userId,
                    name,
                    description,
                    sportType,
                    organizationType,
                    avatar
            );
            return ResponseEntity.ok(club);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 🔽 API MỚI – CLB ĐÃ THAM GIA
    @GetMapping("/my")
    public ResponseEntity<List<ClubResponse>> getMyClubs(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(
                clubQueryService.getMyClubs(userId)
        );
    }

    // 🔽 API MỚI – CLB GỢI Ý
    @GetMapping("/suggested")
    public ResponseEntity<List<ClubResponse>> getSuggestedClubs(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(
                clubQueryService.getSuggestedClubs(userId)
        );
    }
}
