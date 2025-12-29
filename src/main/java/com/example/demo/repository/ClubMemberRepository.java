package com.example.demo.repository;

import com.example.demo.model.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    // Đếm số thành viên trong CLB
    int countByClubId(Long clubId);

    // Kiểm tra user đã tham gia CLB chưa
    boolean existsByClubIdAndUserId(Long clubId, Long userId);
}
