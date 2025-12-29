package com.example.demo.service;

import com.example.demo.dto.ClubResponse;
import com.example.demo.model.Club;
import com.example.demo.repository.ClubMemberRepository;
import com.example.demo.repository.ClubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubQueryService {

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;

    public ClubQueryService(
            ClubRepository clubRepository,
            ClubMemberRepository clubMemberRepository
    ) {
        this.clubRepository = clubRepository;
        this.clubMemberRepository = clubMemberRepository;
    }

    public List<ClubResponse> getMyClubs(Long userId) {
        return clubRepository.findClubsUserJoined(userId)
                .stream()
                .map(club -> toResponse(club, userId))
                .toList();
    }

    public List<ClubResponse> getSuggestedClubs(Long userId) {
        return clubRepository.findSuggestedClubs(userId)
                .stream()
                .map(club -> toResponse(club, userId))
                .toList();
    }

    private ClubResponse toResponse(Club club, Long userId) {
        return new ClubResponse(
                club.getId(),
                club.getName(),
                club.getDescription(),
                club.getSportType(),
                club.getAvatarUrl(),
                clubMemberRepository.countByClubId(club.getId()),
                clubMemberRepository.existsByClubIdAndUserId(club.getId(), userId)
        );
    }
}
