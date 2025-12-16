package com.example.demo.repository;

import com.example.demo.model.Runs;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunsRepository extends JpaRepository<Runs, Long> {
  List<Runs> findByUserId(Long userId);
}
