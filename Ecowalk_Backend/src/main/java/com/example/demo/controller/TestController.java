package com.example.demo.controller;

import com.example.demo.model.Test;
import com.example.demo.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @PostMapping
    public Test createTest(@RequestBody Test test) {
        return testRepository.save(test);
    }

    @GetMapping
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }
}
