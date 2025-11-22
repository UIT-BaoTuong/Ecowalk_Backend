package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.RunPoints;

@Repository
public interface RunPointsRepository extends JpaRepository <RunPoints,Long> {
    List<RunPoints> findByRunId(Long runId);
} 