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

import com.example.demo.model.Runs;
import com.example.demo.model.Users;
import com.example.demo.repository.RunsRepository;
import com.example.demo.repository.UsersRepository;

@RestController
public class RunsController {
    
    @Autowired
    private RunsRepository runsRepository;

    @Autowired
    private UsersRepository usersRepository;

    private static final int POINTS_PER_KM = 100;
    
    @PostMapping("/api/run_activity")
    private ResponseEntity<?> saveRuns(@RequestBody Runs runData) {
        try {
            Runs savedRun = runsRepository.save(runData);

            Long userId = savedRun.getUserId(); 
            Double distance = savedRun.getDistanceKm(); 

            if (userId != null && distance != null) {
                Optional<Users> userOpt = usersRepository.findById(userId);
                
                if (userOpt.isPresent()) {
                    Users user = userOpt.get();
                    
                    int earnedPoints = (int) (distance * POINTS_PER_KM);
                    int currentPoints = user.getCurrentPoints();
                    
                    user.setCurrentPoints(currentPoints + earnedPoints);
                    usersRepository.save(user);
                    
                    System.out.println("Đã cộng " + earnedPoints + " điểm cho User ID: " + userId);
                }
            }

            return new ResponseEntity<>(savedRun, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error saving run: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/run_activity")
    private ResponseEntity<List<Runs>> findAllRuns() {
        List<Runs> runs = runsRepository.findAll();
        return ResponseEntity.ok(runs);
    }

    @PostMapping("api/run_activity/by_id")
    private ResponseEntity<Runs> getRunById(@RequestBody Map<String,String> payload) {
        try {
            Long id = Long.parseLong(payload.get("id"));
            Optional<Runs> optionalRun = runsRepository.findById(id);
            if(optionalRun.isPresent()) {
                Runs run = optionalRun.get();
                return ResponseEntity.ok(run);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("api/run_activity/by_user_id")
    private ResponseEntity<?> getRunByUser(@RequestBody Map<String,String> payload) {
    try {
        Long userId = Long.parseLong(payload.get("userId"));
        
        List<Runs> runs = runsRepository.findByUserId(userId);
        
        return ResponseEntity.ok(runs);
        
    } catch (NumberFormatException e) {
         Map<String, String> response = new HashMap<>();
         response.put("message", "Invalid User ID format");
         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Lỗi Server: " + e.getMessage());
    }
  }
}