package com.example.demo.controller;

import com.example.demo.model.Club;
import com.example.demo.service.ClubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
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
}
