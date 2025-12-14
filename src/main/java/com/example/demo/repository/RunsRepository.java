package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Runs;

@Repository
public interface RunsRepository extends JpaRepository <Runs,Long>{
    List<Runs> findByUserId(Long userId);
}