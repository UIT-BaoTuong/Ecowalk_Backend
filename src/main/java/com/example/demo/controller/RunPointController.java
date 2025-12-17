package com.example.demo.controller;

import com.example.demo.model.RunPoints;
import com.example.demo.repository.RunPointsRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunPointController {

  @Autowired private RunPointsRepository runPointsRepository;

  @PostMapping("/api/run_point")
  public ResponseEntity<RunPoints> saveRunPoint(@RequestBody RunPoints runPoint) {

    RunPoints savedPoint = runPointsRepository.save(runPoint);

    return new ResponseEntity<>(savedPoint, HttpStatus.CREATED);
  }

  @PostMapping("/api/run_points/by_run_id")
  public ResponseEntity<?> getPointsByRunId(@RequestBody Map<String, String> payload) {
    Long runId = Long.parseLong(payload.get("runId"));
    List<RunPoints> points = runPointsRepository.findByRunId(runId);
    if (!points.isEmpty()) {
      return ResponseEntity.ok(points);
    } else {
      Map<String, String> response = new HashMap<>();
      response.put("message", "Không có điểm nào cho runId này.");
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
  }
}
