package com.example.demo.controller;

import com.example.demo.controller.RankingController.RankingResponse;
import com.example.demo.model.RankingDTO;
import com.example.demo.repository.RunsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ranking")
public class RankingController {

    @Autowired
    private RunsRepository runsRepository;

    @GetMapping
    public ResponseEntity<?> getRanking(@RequestParam String type, @RequestParam(required = false) Long currentUserId) {
        LocalDateTime startDate;
        LocalDateTime now = LocalDateTime.now();

        switch (type.toLowerCase()) {
            case "month":
                startDate = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
                break;
            case "year":
                startDate = now.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0);
                break;
            default: // all time
                startDate = LocalDateTime.of(2000, 1, 1, 0, 0);
                break;
        }

        List<RankingDTO> fullList = runsRepository.getTopRunners(startDate);
        List<RankingDTO> top10 = fullList.stream().limit(10).collect(Collectors.toList());

        Integer myRankNum = null;
        if (currentUserId != null) {
            for (int i = 0; i < top10.size(); i++) {
                if (top10.get(i).getUserId().equals(currentUserId)) {
                    myRankNum = i + 1;
                    break;
                }
            }
            
            if (myRankNum == null) {
                myRankNum = runsRepository.getUserRank(currentUserId, startDate);
            }
        }
        
        RankingResponse response = new RankingResponse();
        response.setTopList(top10);
        response.setMyRank(myRankNum != null ? String.valueOf(myRankNum) : "--");

        return ResponseEntity.ok(response);
    }

    public static class RankingResponse {
        private List<RankingDTO> topList;
        private String myRank;

        public RankingResponse() {}

        public List<RankingDTO> getTopList() {
            return topList;
        }

        public void setTopList(List<RankingDTO> topList) {
            this.topList = topList;
        }

        public String getMyRank() {
            return myRank;
        }

        public void setMyRank(String myRank) {
            this.myRank = myRank;
        }
    }
}