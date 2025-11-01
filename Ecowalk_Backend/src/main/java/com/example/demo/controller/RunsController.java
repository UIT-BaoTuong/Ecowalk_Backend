package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Runs;
import com.example.demo.repository.RunsRepository;

@RestController
public class RunsController {
    @Autowired
    private RunsRepository runsRepository;
    
    @PostMapping("/api/run_activity")
    private ResponseEntity<Runs> runActivity(@RequestBody Runs runData)
    {
        Runs savedRun = runsRepository.save(runData);
        return new ResponseEntity<>(savedRun, HttpStatus.CREATED);
    }
}
