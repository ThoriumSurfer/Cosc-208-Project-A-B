package org.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/*The main Bulk of the program, compares all Buses, Trains, and backupRides between each respective vehicle's arraylist
and each other to determine the best mode of transportation for each and the best transportation overall using a
weighted score system where the user will input what aspect of each transportation system they value more*/
public class Recommendation_Algorithm {
    
    //The first 4 determine the scores between the vehicles within arraylist and totalscore helps determine the best
    //method of transportation overall
    private int fareScore;
    
    private int weatherScore;
    
    private int walkScore;
    
    private int travelScore;
    
    private int totalScore;
    
    private Scanner userInput;
    
    //Remembers the overall winner so the decision tree can end with it
    private Transportation bestVehicleOverall;
    
    //Holds which variable will have the highest weighted score
    private int[] priorities = new int[4];
    
    private int[] weightedScore = new int[4];
    
    //These 4 arraylists hold all of the vehicles that will be compared and recommended
    private ArrayList<Transportation> buses;
    
    private ArrayList<Transportation> trains;
    
    private ArrayList<Transportation> backupRides;
    
    private ArrayList<Transportation> totalTransport = new ArrayList<>();
    
    //input from Bus_Train_Input class
    private Bus_Train_Input input;
    
    
    public static void main(String[] args){
    
    }
    
    //Constructor for Recommendation_algorithm Class
    public Recommendation_Algorithm(Bus_Train_Input input, Bus_Train_Logic logic) {
        this.input = input;
        this.userInput = input.getUserInput();
        this.buses = logic.getViableBuses();
        this.trains = logic.getViableTrains();
        this.backupRides = logic.getViableBackupRides();
        totalTransport.addAll(buses);
        totalTransport.addAll(trains);
        totalTransport.addAll(backupRides);
    }
    
    //Will Determine which methods of vehicles are best and print them back out to the user!
    public void Determine_Priority() {
        
        //The 4 individual categories that will Determine the best mdoe of transportation
        String[] categories = {
                "Fare", "Walking Time", "Travel Time", "Weather"
        };
        
        for (int i = 0; i < priorities.length; i++) {
            boolean validScore;
            
            int choice = 0;
            
            do {
                validScore = true;
                
                //try catch will ask the user to input which of the 4 variables they value the most in their
                //vehicle ranking
                try {
                    System.out.println("\nHow much do you prioritize " + categories[i] + " use 1 to 4, 4 " +
                            "being the most important and 1 being least important (1-4): ");
                    
                    choice = userInput.nextInt();
                    
                } catch (InputMismatchException e) {
                    
                    userInput.next();
                    
                    validScore = false;
                    
                    System.out.println("\nPlease enter an integer.");
                    
                }
                
                //Multiple checks to see if the user actually inputed 1-4 and has not repeated as score
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
        
        //How much each score will be increased starting from 1 being inputted up to 4
        int[] priorityWeights = {1, 3, 6, 10};
        
        for (int i = 0; i < priorities.length; i++) {
            
            weightedScore[i] = priorityWeights[priorities[i] - 1];
            
        }
        
        //Resets all Scores
        for (Transportation transport : totalTransport) {
            transport.resetScore();
        }
        
        
        //Bus only scoring
        
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
        
        
        //Recommends the best bus
        
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
        
        
        //Resets the scores before scoring the trains
        
        for (Transportation transport : totalTransport) {
            
            transport.resetScore();
            
        }
        
        
        //Train only scoring
        
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
        
        
        //Recommends the best Trains
        
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


// BackupRide only scoring
        
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


        //Recommends the best backupRide
        
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
        
        
        //Resets scores before scoring all transports together
        
        for (Transportation transport : totalTransport) {
            transport.resetScore();
        }
        
        
        //Scores Buses vs Trains Vs backupRides
        
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
        
        
        //Recommends the absolute best transportation for the user
        
        if (!totalTransport.isEmpty()) {
            
            Transportation recommendedOverall = totalTransport.get(0);
            
            for (int i = 1; i < totalTransport.size(); i++) {
                
                if (totalTransport.get(i).getTotalScore() > recommendedOverall.getTotalScore()) {
                    
                    recommendedOverall = totalTransport.get(i);
                    
                }
            }
            
            bestVehicleOverall = recommendedOverall;
            
            System.out.println("\nThe Best Transportation for your current needs:");
            
            System.out.println("\n" + recommendedOverall);
            
            System.out.println("Total Score: " + recommendedOverall.getTotalScore());
            
        } else {
            
            System.out.println("\nNo transportation options available for you currently.");
            
        }
    }
    
    public Transportation getBestVehicleOverall() {
        
        return bestVehicleOverall;
        
    }
    
}