package com.example.demo.controller;

import com.example.demo.model.Runs;
import com.example.demo.repository.RunsRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunsController {
  @Autowired private RunsRepository runsRepository;

  @PostMapping("/api/run_activity")
  private ResponseEntity<Runs> saveRuns(@RequestBody Runs runData) {
    Runs savedRun = runsRepository.save(runData);
    return new ResponseEntity<>(savedRun, HttpStatus.CREATED);
  }

  @GetMapping("/api/run_activity")
  private ResponseEntity<List<Runs>> findAllRuns() {
    List<Runs> runs = runsRepository.findAll();
    return ResponseEntity.ok(runs);
  }

  @PostMapping("api/run_activity/by_id")
  private ResponseEntity<Runs> getRunById(@RequestBody Map<String, String> payload) {
    Long id = Long.parseLong(payload.get("id"));
    Optional<Runs> optionalRun = runsRepository.findById(id);
    if (optionalRun.isPresent()) {
      Runs run = optionalRun.get();
      return ResponseEntity.ok(run);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("api/run_activity/by_user_id")
  private ResponseEntity<?> getRunByUser(@RequestBody Map<String, String> payload) {
    Long userId = Long.parseLong(payload.get("userId"));
    List<Runs> runs = runsRepository.findByUserId(userId);
    if (!runs.isEmpty()) {
      return ResponseEntity.ok(runs);
    } else {
      Map<String, String> response = new HashMap<>();
      response.put("message", "Không có buổi chạy nào cho người dùng này.");
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
  }
}
