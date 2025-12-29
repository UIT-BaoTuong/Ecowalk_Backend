package com.example.demo.repository;

import com.example.demo.model.ClubMember;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    // Đếm số thành viên trong CLB
    int countByClub_Id(Long clubId);

    // Kiểm tra user đã tham gia CLB chưa
    boolean existsByClub_IdAndUser_Id(Long clubId, Long userId);
    List<ClubMember> findByUser_Id(Long userId);
}
