package com.example.demo.model;

import java.util.List;

public class HomeResponse {
    private String fullName;
    private String avatarUrl;
    private DailyStat today;
    private List<WeeklyStat> weeklyChart;
    
    private List<CalendarStat> calendarHistory; 

    public HomeResponse() {}

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public DailyStat getToday() { return today; }
    public void setToday(DailyStat today) { this.today = today; }

    public List<WeeklyStat> getWeeklyChart() { return weeklyChart; }
    public void setWeeklyChart(List<WeeklyStat> weeklyChart) { this.weeklyChart = weeklyChart; }

    public List<CalendarStat> getCalendarHistory() { return calendarHistory; }
    public void setCalendarHistory(List<CalendarStat> calendarHistory) { this.calendarHistory = calendarHistory; }

    public static class CalendarStat {
        private String date;      
        private double distance;  

        public CalendarStat(String date, double distance) {
            this.date = date;
            this.distance = distance;
        }

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        public double getDistance() { return distance; }
        public void setDistance(double distance) { this.distance = distance; }
    }

    public static class DailyStat {
        private double distance;
        private double calories;
        private int timeMinutes;
        private double goal = 5.0;

        public double getDistance() { return distance; }
        public void setDistance(double distance) { this.distance = distance; }
        public double getCalories() { return calories; }
        public void setCalories(double calories) { this.calories = calories; }
        public int getTimeMinutes() { return timeMinutes; }
        public void setTimeMinutes(int timeMinutes) { this.timeMinutes = timeMinutes; }
        public double getGoal() { return goal; }
        public void setGoal(double goal) { this.goal = goal; }
    }

    public static class WeeklyStat {
        private String dayName;
        private double distance;

        public WeeklyStat(String dayName, double distance) {
            this.dayName = dayName;
            this.distance = distance;
        }

        public String getDayName() { return dayName; }
        public void setDayName(String dayName) { this.dayName = dayName; }
        public double getDistance() { return distance; }
        public void setDistance(double distance) { this.distance = distance; }
    }
}