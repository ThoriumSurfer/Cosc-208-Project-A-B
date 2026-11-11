package org.example;

import java.time.LocalTime;

//Determines how the objects within the ArrayLists behave they use, Vehiclename, fare, walkTime, travelTime, weather
//serviceoperation, AND deadline
public class Transportation {
    
    private double fare;
    private double walkTime;
    private double travelTime;
    private int totalScore = 0;
    private String weather;
    private String vehicleName;
    private boolean serviceOperation;
    private LocalTime deadline;
    
    public static void main(String[] args) {
    
        
    }
    
    //Class Constructor
    public Transportation(String vehicleName, double fare, double walkTime, double travelTime,
                          String weather, boolean serviceOperation, LocalTime deadline) {
        this.vehicleName = vehicleName;
        this.fare = fare;
        this.walkTime = walkTime;
        this.travelTime = travelTime;
        this.weather = weather;
        this.serviceOperation = serviceOperation;
        this.deadline = deadline;
    }
    
    //All Getters
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
    
    public boolean getServiceOperation() {
        
        return serviceOperation;
        
    }
    
    public LocalTime getDeadline() {
        
        return deadline;
        
    }
    
    //Allows for a proper display when printing the entire ArrayList as a string
    @Override
    public String toString() {
        return "Vehicle: " + vehicleName + " \nFare: " + fare + ", Walking Time: " + walkTime +
                ", Travel Time: " + travelTime;
    }
    
}