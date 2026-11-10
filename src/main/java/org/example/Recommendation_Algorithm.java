package org.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Recommendation_Algorithm {
    
    private int fareScore;
    
    private int weatherScore;
    
    private int walkScore;
    
    private int travelScore;
    
    private int totalScore;
    
    private Scanner userInput;
    
    private int[] priorities = new int[4];
    
    private int[] weightedScore = new int[4];
    
    private ArrayList<Transportation> buses;
    
    private ArrayList<Transportation> trains;
    
    private ArrayList<Transportation> backupRides;
    
    private ArrayList<Transportation> totalTransport = new ArrayList<>();
    
    private Bus_Train_Input input;
    
    
    public static void main(String[] args){
    
    }
    
    public Recommendation_Algorithm(Bus_Train_Input input) {
        this.input = input;
        this.userInput = input.getUserInput();
        this.buses = input.getBuses();
        this.trains = input.getTrains();
        this.backupRides = input.getBackupRides();
        totalTransport.addAll(buses);
        totalTransport.addAll(trains);
        totalTransport.addAll(backupRides);
    }
    
    public void Determine_Priority() {
        
        String[] categories = {
                "Fare", "Walking Time", "Travel Time", "Weather"
        };
        
        for (int i = 0; i < priorities.length; i++) {
            boolean validScore;
            
            int choice = 0;
            
            do {
                validScore = true;
                
                try {
                    System.out.println("\nHow much do you prioritize " + categories[i] + " use 1 to 4, 4 " +
                            "being the most important and 1 being least important (1-4): ");
                    
                    choice = userInput.nextInt();
                    
                } catch (InputMismatchException e) {
                    
                    userInput.next();
                    
                    validScore = false;
                    
                    System.out.println("\nPlease enter an integer.");
                    
                }
                
                if (choice < 1 || choice > 4) {
                    
                    validScore = false;
                    
                } else {
                    for (int j = 0; j < i; j++) {
                        if (priorities[j] == choice) {
                            
                            validScore = false;
                            
                            break;
                        }
                    }
                }
                
                if (validScore) {
                    priorities[i] = choice;
                } else {
                    System.out.println("\nInvalid or already-used priority. Try again.");
                }
                
            } while (!validScore);
        }
        
        int[] priorityWeights = {1, 3, 6, 10};
        
        for (int i = 0; i < priorities.length; i++) {
            
            weightedScore[i] = priorityWeights[priorities[i] - 1];
            
        }
        
        // RESET ALL SCORES
        for (Transportation transport : totalTransport) {
            transport.resetScore();
        }
        
        
        // BUS-ONLY SCORING
        
        for (int i = 0; i < buses.size(); i++) {
            
            for (int j = i + 1; j < buses.size(); j++) {
                
                if (buses.get(i).getFare() < buses.get(j).getFare()) {
                    buses.get(i).addScore(weightedScore[0]);
                    
                } else if (buses.get(j).getFare() < buses.get(i).getFare()) {
                    buses.get(j).addScore(weightedScore[0]);
                }
                
                if (buses.get(i).getWalkTime() < buses.get(j).getWalkTime()) {
                    buses.get(i).addScore(weightedScore[1]);
                    
                } else if (buses.get(j).getWalkTime() < buses.get(i).getWalkTime()) {
                    buses.get(j).addScore(weightedScore[1]);
                }
                
                if (buses.get(i).getTravelTime() < buses.get(j).getTravelTime()) {
                    buses.get(i).addScore(weightedScore[2]);
                    
                } else if (buses.get(j).getTravelTime() < buses.get(i).getTravelTime()) {
                    buses.get(j).addScore(weightedScore[2]);
                }
                
                if (buses.get(i).getWeather().equalsIgnoreCase("Sunny") &&
                        buses.get(j).getWeather().equalsIgnoreCase("Intense Weather")) {
                    
                    buses.get(i).addScore(weightedScore[3]);
                    
                } else if (buses.get(j).getWeather().equalsIgnoreCase("Sunny") &&
                        buses.get(i).getWeather().equalsIgnoreCase("Intense Weather")) {
                    
                    buses.get(j).addScore(weightedScore[3]);
                }
            }
        }
        
        
        // BEST BUS RECOMMENDATION
        
        if (!buses.isEmpty()) {
            
            Transportation recommendedBus = buses.get(0);
            
            for (int i = 1; i < buses.size(); i++) {
                
                if (buses.get(i).getTotalScore() > recommendedBus.getTotalScore()) {
                    recommendedBus = buses.get(i);
                }
            }
            
            System.out.println("\nThe Best Bus for you is:");
            
            System.out.println("\n" + recommendedBus);
            
            System.out.println("Total Score: " + recommendedBus.getTotalScore());
            
        } else {
            
            System.out.println("\nThere are no buses available for you currently.");
            
        }
        
        
        // RESET SCORES BEFORE TRAIN SCORING
        
        for (Transportation transport : totalTransport) {
            
            transport.resetScore();
            
        }
        
        
        // TRAIN-ONLY SCORING
        
        for (int i = 0; i < trains.size(); i++) {
            
            for (int j = i + 1; j < trains.size(); j++) {
                
                if (trains.get(i).getFare() < trains.get(j).getFare()) {
                    
                    trains.get(i).addScore(weightedScore[0]);
                    
                } else if (trains.get(j).getFare() < trains.get(i).getFare()) {
                    
                    trains.get(j).addScore(weightedScore[0]);
                    
                }
                
                if (trains.get(i).getWalkTime() < trains.get(j).getWalkTime()) {
                    
                    trains.get(i).addScore(weightedScore[1]);
                    
                } else if (trains.get(j).getWalkTime() < trains.get(i).getWalkTime()) {
                    trains.get(j).addScore(weightedScore[1]);
                }
                
                if (trains.get(i).getTravelTime() < trains.get(j).getTravelTime()) {
                    
                    trains.get(i).addScore(weightedScore[2]);
                    
                    
                } else if (trains.get(j).getTravelTime() < trains.get(i).getTravelTime()) {
                    
                    trains.get(j).addScore(weightedScore[2]);
                    
                }
                
                if (trains.get(i).getWeather().equalsIgnoreCase("Sunny") &&
                        
                        trains.get(j).getWeather().equalsIgnoreCase("Intense Weather")) {
                    
                    trains.get(i).addScore(weightedScore[3]);
                    
                } else if (trains.get(j).getWeather().equalsIgnoreCase("Sunny") &&
                        
                        trains.get(i).getWeather().equalsIgnoreCase("Intense Weather")) {
                    
                    trains.get(j).addScore(weightedScore[3]);
                }
            }
        }
        
        
        // BEST TRAIN RECOMMENDATION
        
        if (!trains.isEmpty()) {
            
            Transportation recommendedTrain = trains.get(0);
            
            for (int i = 1; i < trains.size(); i++) {
                
                if (trains.get(i).getTotalScore() > recommendedTrain.getTotalScore()) {
                    recommendedTrain = trains.get(i);
                }
            }
            
            System.out.println("\nThe Best Train for you is:");
            
            System.out.println("\n" + recommendedTrain);
            
            System.out.println("Total Score: " + recommendedTrain.getTotalScore());
            
        } else {
            System.out.println("\nThere are no trains available for you currently.");
        }
        
        for (Transportation transport : totalTransport) {
            
            transport.resetScore();
            
        }


// BACKUP RIDE-ONLY SCORING
        
        for (int i = 0; i < backupRides.size(); i++) {
            
            for (int j = i + 1; j < backupRides.size(); j++) {
                
                if (backupRides.get(i).getFare() < backupRides.get(j).getFare()) {
                    
                    backupRides.get(i).addScore(weightedScore[0]);
                    
                } else if (backupRides.get(j).getFare() < backupRides.get(i).getFare()) {
                    
                    backupRides.get(j).addScore(weightedScore[0]);
                    
                }
                
                if (backupRides.get(i).getWalkTime() < backupRides.get(j).getWalkTime()) {
                    
                    backupRides.get(i).addScore(weightedScore[1]);
                    
                } else if (backupRides.get(j).getWalkTime() < backupRides.get(i).getWalkTime()) {
                    
                    backupRides.get(j).addScore(weightedScore[1]);
                    
                }
                
                if (backupRides.get(i).getTravelTime() < backupRides.get(j).getTravelTime()) {
                    
                    backupRides.get(i).addScore(weightedScore[2]);
                    
                } else if (backupRides.get(j).getTravelTime() < backupRides.get(i).getTravelTime()) {
                    
                    backupRides.get(j).addScore(weightedScore[2]);
                    
                }
                
                if (backupRides.get(i).getWeather().equalsIgnoreCase("Sunny") &&
                        backupRides.get(j).getWeather().equalsIgnoreCase("Intense Weather")) {
                    
                    backupRides.get(i).addScore(weightedScore[3]);
                    
                } else if (backupRides.get(j).getWeather().equalsIgnoreCase("Sunny") &&
                        backupRides.get(i).getWeather().equalsIgnoreCase("Intense Weather")) {
                    
                    backupRides.get(j).addScore(weightedScore[3]);
                    
                }
            }
        }


// BEST BACKUP RIDE RECOMMENDATION
        
        if (!backupRides.isEmpty()) {
            
            Transportation recommendedBackupRide = backupRides.get(0);
            
            for (int i = 1; i < backupRides.size(); i++) {
                
                if (backupRides.get(i).getTotalScore() > recommendedBackupRide.getTotalScore()) {
                    
                    recommendedBackupRide = backupRides.get(i);
                    
                }
            }
            
            System.out.println("\nThe Best Backup Ride for you is:");
            
            System.out.println("\n" + recommendedBackupRide);
            
            System.out.println("Total Score: " + recommendedBackupRide.getTotalScore());
            
        } else {
            
            System.out.println("\nThere are no backup rides available for you currently.");
            
        }
        
        
        // RESET SCORES BEFORE OVERALL SCORING
        
        for (Transportation transport : totalTransport) {
            transport.resetScore();
        }
        
        
        // OVERALL SCORING: BUSES VS TRAINS
        
        for (int i = 0; i < totalTransport.size(); i++) {
            
            for (int j = i + 1; j < totalTransport.size(); j++) {
                
                if (totalTransport.get(i).getFare() < totalTransport.get(j).getFare()) {
                    
                    totalTransport.get(i).addScore(weightedScore[0]);
                    
                } else if (totalTransport.get(j).getFare() < totalTransport.get(i).getFare()) {
                    
                    totalTransport.get(j).addScore(weightedScore[0]);
                    
                }
                
                if (totalTransport.get(i).getWalkTime() < totalTransport.get(j).getWalkTime()) {
                    
                    totalTransport.get(i).addScore(weightedScore[1]);
                    
                    
                } else if (totalTransport.get(j).getWalkTime() < totalTransport.get(i).getWalkTime()) {
                    
                    totalTransport.get(j).addScore(weightedScore[1]);
                    
                }
                
                if (totalTransport.get(i).getTravelTime() < totalTransport.get(j).getTravelTime()) {
                    
                    totalTransport.get(i).addScore(weightedScore[2]);
                    
                    
                } else if (totalTransport.get(j).getTravelTime() < totalTransport.get(i).getTravelTime()) {
                    
                    totalTransport.get(j).addScore(weightedScore[2]);
                    
                }
                
                if (totalTransport.get(i).getWeather().equalsIgnoreCase("Sunny") &&
                        
                        totalTransport.get(j).getWeather().equalsIgnoreCase("Intense Weather")) {
                    
                    totalTransport.get(i).addScore(weightedScore[3]);
                    
                } else if (totalTransport.get(j).getWeather().equalsIgnoreCase("Sunny") &&
                        totalTransport.get(i).getWeather().equalsIgnoreCase("Intense Weather")) {
                    
                    totalTransport.get(j).addScore(weightedScore[3]);
                    
                }
            }
        }
        
        
        // BEST OVERALL RECOMMENDATION
        
        if (!totalTransport.isEmpty()) {
            
            Transportation recommendedOverall = totalTransport.get(0);
            
            for (int i = 1; i < totalTransport.size(); i++) {
                
                if (totalTransport.get(i).getTotalScore() > recommendedOverall.getTotalScore()) {
                    
                    recommendedOverall = totalTransport.get(i);
                    
                }
            }
            
            System.out.println("\nThe Best Transportation for your current needs:");
            
            System.out.println("\n" + recommendedOverall);
            
            System.out.println("Total Score: " + recommendedOverall.getTotalScore());
            
        } else {
            
            System.out.println("\nNo transportation options available for you currently.");
            
        }
    }
    
}