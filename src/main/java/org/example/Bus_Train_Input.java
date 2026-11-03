package org.example;

//import org.example.InvalidWeatherException;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;




public class Bus_Train_Input {
    
    private Scanner userInput = new Scanner(System.in);
    
    private LocalTime deadline;
    
    private LocalTime currentTime;
    
    private double budget;
    
    private String weather;
    
    private int busWalkTime;
    
    private int trainWalkTime;
    
    private boolean busService;
    
    private boolean trainService;
    
    private int busTravelTime;
    
    private int trainTravelTime;
    
    private int busFare;
    
    private int trainFare;
    
    public static void main(String[] args) {
        
        System.out.println("Hello, World!");

    }
    
    public Bus_Train_Input() {
        this.deadline = deadline;
        this.currentTime = currentTime;
        this.budget = budget;
        this.weather = weather;
        this.busWalkTime = busWalkTime;
        this.trainWalkTime = trainWalkTime;
        this.busService = busService;
        this.trainService = trainService;
        this.busTravelTime = busTravelTime;
        this.trainTravelTime = trainTravelTime;
        this.busFare = busFare;
        this.trainFare = trainFare;
    }
    
    public void Bus_Inputs ()
    {
        boolean validInput = false;
        
        while (!validInput) {
            try {
                
                System.out.println("\nPlease insert the deadline for the bus arrival: ");
                deadline = LocalTime.parse(userInput.nextLine());
                
                System.out.println("\nEnter your budget: ");
                budget = userInput.nextDouble();
                
                System.out.println("\nPlease enter either 'Sunny', Cloudy', 'Rainy'. or 'Intense Weather: ");
                weather = userInput.nextLine();
                
                if (!weather.equalsIgnoreCase("Sunny") &&
                        !weather.equalsIgnoreCase("Cloudy") &&
                        !weather.equalsIgnoreCase("Rainy") &&
                        !weather.equalsIgnoreCase("Intense Weather")) {
                    
                    throw new InvalidInputException("\nYou didn't input the correct weather!");
                }
                
                System.out.println("\nPlease enter the walk time it takes to reach the bus stop: ");
                this.busWalkTime = userInput.nextInt();
                
                System.out.println("\nIs the bus in service? Please type yes or no: ");
                String serviceAnswer = userInput.nextLine();
                
                if (!serviceAnswer.equalsIgnoreCase("Yes") &&
                        !serviceAnswer.equalsIgnoreCase("No")
                        ) {
                    
                    throw new InvalidInputException("\nYou didn't input the correct weather!");
                } else {
                    
                    busService = true;
                    
                }
                
                System.out.println("\nPlease enter the bus travel time: ");
                busTravelTime = userInput.nextInt();
                
                System.out.println("\nPlease enter the bus fare: ");
                busFare = userInput.nextInt();
                
                validInput = true;
                
            } catch (InputMismatchException | DateTimeParseException e) {
                System.out.println("\nThat input is invalid, please enter the correct value");
                userInput.nextLine(); // Clears invalid input
            } catch (InvalidInputException e) {
                
                System.out.println("\n" + e.getMessage());
            
            }
        }
        
    }
    
    public void Train_Inputs () {
        boolean validInput = false;
        
        while (!validInput) {
            try {
                
                System.out.println("\nPlease insert the deadline for the train arrival: ");
                deadline = LocalTime.parse(userInput.nextLine());
                
                System.out.println("\nEnter your budget: ");
                budget = userInput.nextDouble();
                
                System.out.println("\nPlease enter either 'Sunny', Cloudy', 'Rainy'. or 'Intense Weather: ");
                weather = userInput.nextLine();
                
                if (!weather.equalsIgnoreCase("Sunny") &&
                        !weather.equalsIgnoreCase("Cloudy") &&
                        !weather.equalsIgnoreCase("Rainy") &&
                        !weather.equalsIgnoreCase("Intense Weather")) {
                    
                    throw new InvalidInputException("\nYou didn't input the correct weather!");
                }
                
                System.out.println("\nPlease enter the walk time it takes to reach the station: ");
                trainWalkTime = userInput.nextInt();
                
                System.out.println("\nIs the train in service? Please type yes or no: ");
                String serviceAnswer = userInput.nextLine();
                
                if (!serviceAnswer.equalsIgnoreCase("Yes") &&
                        !serviceAnswer.equalsIgnoreCase("No")
                ) {
                    
                    throw new InvalidInputException("\nYou didn't input the correct weather!");
                } else {
                    
                    trainService = true;
                    
                }
                
                System.out.println("\nPlease enter the train travel time: ");
                trainTravelTime = userInput.nextInt();
                
                System.out.println("\nPlease enter the train fare: ");
                trainFare = userInput.nextInt();
                
                validInput = true;
                
            } catch (InputMismatchException | DateTimeParseException e) {
                System.out.println("\nThat input is invalid, please enter the correct value");
                userInput.nextLine(); // Clears invalid input
            } catch (InvalidInputException e) {
                
                System.out.println("\n" + e.getMessage());
                
            }
        }
        
    }
    
    public LocalTime getDeadline() {
        return deadline;
    }
    
    public LocalTime  getCurrentTime() {
        return currentTime;
    }
    
    public double getBudget() {
        return budget;
    }
    
    public String getWeather() {
        return weather;
    }
    
    public int getBusWalkTime() {
        return busWalkTime;
    }
    
    public int getTrainWalkTime() {
        return trainWalkTime;
    }
    
    public boolean getBusService() {
        return busService;
    }
    
    public boolean getTrainService() {
        return trainService;
    }
    
    public int getBusTravelTime() {
        return busTravelTime;
    }
    
    public int getTrainTravelTime() {
        return trainTravelTime;
    }
    
    public int getBusFare() {
        return busFare;
    }
    
    public int getTrainFare() {
        return trainFare;
    }
    
    /*
    LocalTime deadline, LocalTime currentTime, double budget, String weather,
                           int busWalkTime, int trainWalkTime, boolean busService, boolean trainService,
                           int busTravelTime, int trainTravelTime, int busFare, int trainFare
    
     */
    
    /*
    Scanner userInput, LocalTime deadline, LocalTime currentTIme, double budget,
                              String weather, int trainWalkTime, boolean trainService, int trainTravelTime,
                              int trainFare
    
     */
    
    /*
    private Scanner userInput = new Scanner(System.in);
    
    private String deadline;
    
    private LocalTime currentTime;
    
    private double budget;
    
    private String weather;
    
    private int walkTime;
    
    private boolean busService;
    
    private boolean trainService;
    
    private int busTravelTime;
    
    private int trainTravelTime;
    
    private int busFare;
    
    private int trainFare;
     */
    
}