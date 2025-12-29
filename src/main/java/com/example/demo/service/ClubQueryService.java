package com.example.demo.service;

import com.example.demo.dto.ClubResponse;
import com.example.demo.model.Club;
import com.example.demo.model.ClubMember;
import com.example.demo.model.Users;
import com.example.demo.repository.ClubMemberRepository;
import com.example.demo.repository.ClubRepository;
import com.example.demo.repository.UsersRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubQueryService {

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final UsersRepository usersRepository;

    public ClubQueryService(
            ClubRepository clubRepository,
            ClubMemberRepository clubMemberRepository,
            UsersRepository usersRepository
    ) {
        this.clubRepository = clubRepository;
        this.clubMemberRepository = clubMemberRepository;
        this.usersRepository = usersRepository;
    }

    /* ================= JOIN CLUB ================= */

    public void joinClub(Long clubId, Long userId) {
        if (clubMemberRepository.existsByClub_IdAndUser_Id(clubId, userId)) {
            return; // đã tham gia
        }

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("CLB không tồn tại"));

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        ClubMember member = new ClubMember();
        member.setClub(club);
        member.setUser(user);

        clubMemberRepository.save(member);
    }

    /* ================= MY CLUBS ================= */
    public List<ClubResponse> getMyClubs(Long userId) {
        return clubRepository.findClubsUserJoined(userId)
                .stream()
                .map(club -> toResponse(club, userId))
                .toList();
    }

    /* ================= SUGGESTED ================= */
    public List<ClubResponse> getSuggestedClubs(Long userId) {
        return clubRepository.findSuggestedClubs(userId)
                .stream()
                .map(club -> toResponse(club, userId))
                .toList();
    }

     /* ================= MAPPER ================= */
    private ClubResponse toResponse(Club club, Long userId) {
        return new ClubResponse(
                club.getId(),
                club.getName(),
                club.getDescription(),
                club.getSportType(),
                club.getAvatarUrl(),
                clubMemberRepository.countByClub_Id(club.getId()),
                clubMemberRepository.existsByClub_IdAndUser_Id(club.getId(), userId)
        );
    }

}
