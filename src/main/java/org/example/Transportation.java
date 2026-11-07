package org.example;

public class Transportation {
    
    private double fare;
    private double walkTime;
    private double travelTime;
    private int totalScore = 0;
    private String weather;
    private String vehicleName;
    
    public static void main(String[] args) {
        
        System.out.println("Hello, World!");
        
    }
    
    public Transportation(String vehicleName, double fare, double walkTime, double travelTime, String weather) {
        this.vehicleName = vehicleName;
        this.fare = fare;
        this.walkTime = walkTime;
        this.travelTime = travelTime;
        this.weather = weather;
    }
    
    public double getFare() {
        return fare;
    }
    
    public double getWalkTime() {
        return walkTime;
    }
    
    public double getTravelTime() {
        return travelTime;
    }
    
    public String getWeather() {
        return weather;
    }
    
    public void addScore(int points) {
        totalScore += points;
    }
    
    public int getTotalScore() {
        return totalScore;
    }
    
    public void resetScore() {
        totalScore = 0;
    }
    public String getVehicleName() {
        return vehicleName;
    }
    
    
    @Override
    public String toString() {
        return "Fare: " + fare +
                ", Walking Time: " + walkTime +
                ", Travel Time: " + travelTime;
    }
    
}