package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.RunsRepository;
import com.example.demo.repository.UsersRepository;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
public class HomeController {

  @Autowired private RunsRepository runsRepository;
  @Autowired private UsersRepository usersRepository;

  @GetMapping("/dashboard")
  public ResponseEntity<?> getDashboard(@RequestParam Long userId) {
    HomeResponse response = new HomeResponse();

    usersRepository
        .findById(userId)
        .ifPresent(
            user -> {
              response.setFullName(user.getFullName());
              response.setAvatarUrl(user.getAvatarUrl());
              System.out.println("SERVER CHECK - ID: " + userId);
              System.out.println("SERVER CHECK - Avatar DB: " + user.getAvatarUrl());
            });

    HomeResponse.DailyStat today = new HomeResponse.DailyStat();
    Double dist = runsRepository.getTodayDistance(userId);
    Integer seconds = runsRepository.getTodayTimeSeconds(userId);

    today.setDistance(dist != null ? dist : 0.0);
    today.setTimeMinutes(seconds != null ? seconds / 60 : 0);
    today.setCalories(today.getDistance() * 60); // Công thức giả định: 1km = 60kcal
    response.setToday(today);

    List<Runs> recentRuns = runsRepository.getLast7DaysRuns(userId);
    List<HomeResponse.WeeklyStat> chartData = new ArrayList<>();

    Map<String, Double> distMap = new LinkedHashMap<>();
    LocalDate now = LocalDate.now();
    for (int i = 6; i >= 0; i--) {
      LocalDate d = now.minusDays(i);
      String dayKey =
          d.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH); // Mon, Tue...
      distMap.put(dayKey, 0.0);
    }

    for (Runs r : recentRuns) {
      String dayKey =
          r.getStartTime().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
      if (distMap.containsKey(dayKey)) {
        distMap.put(dayKey, distMap.get(dayKey) + r.getDistanceKm());
      }
    }

    for (Map.Entry<String, Double> entry : distMap.entrySet()) {
      chartData.add(new HomeResponse.WeeklyStat(entry.getKey(), entry.getValue()));
    }
    response.setWeeklyChart(chartData);

    List<Object[]> stats = runsRepository.getCalendarStats(userId);
    List<HomeResponse.CalendarStat> history = new ArrayList<>();

    for (Object[] row : stats) {
      String dateStr = (String) row[0];

      double historyDist = ((Number) row[1]).doubleValue();
      history.add(new HomeResponse.CalendarStat(dateStr, historyDist));
    }
    response.setCalendarHistory(history);

    return ResponseEntity.ok(response);
  }
}
