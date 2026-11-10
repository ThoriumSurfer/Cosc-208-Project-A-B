package org.example;

//import org.example.InvalidWeatherException;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;




public class Bus_Train_Input {
    
    private Scanner userInput = new Scanner(System.in);
    
    private LocalTime deadline;
    
    private LocalTime currentTime = LocalTime.now();
    
    private double budget;
    
    private String weather;
    
    private double busWalkTime;
    
    private double trainWalkTime;
    
    private boolean busService;
    
    private boolean trainService;
    
    private double busTravelTime;
    
    private double trainTravelTime;
    
    private double busFare;
    
    private double trainFare;
    
    private double backupRideWalkTime;
    
    private boolean backupRideService;
    
    private double backupRideTravelTime;
    
    private double backupRideFare;
    
    private ArrayList<Transportation> backupRides = new ArrayList<>();
    
    private Transportation backupRideObject;
    
    private ArrayList<Transportation> buses = new ArrayList<>();
    
    private ArrayList<Transportation> trains = new ArrayList<>();
    
    private Transportation busObject;
    
    private Transportation trainObject;
    
    public static void main(String[] args) {

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
    
    public void Budget_Input() {
        
        System.out.println("\nEnter your budget: ");
        
        budget = userInput.nextDouble();
        
        userInput.nextLine();
    }
    
    public void Bus_Inputs ()
    {
        boolean validInput = false;
        
        while (!validInput) {
            try {
                
                System.out.println("\nPlease insert the deadline for the bus arrival (HH:mm): ");
                deadline = LocalTime.parse(userInput.nextLine());
                
                System.out.println("\nIs the bus in service? Please type yes or no: ");
                String serviceAnswer = userInput.nextLine();
                
                if (!serviceAnswer.equalsIgnoreCase("Yes") &&
                        !serviceAnswer.equalsIgnoreCase("No")
                        ) {
                    
                    throw new InvalidInputException("\nPlease input yes or no!");
                } else {
                    
                    busService = serviceAnswer.equalsIgnoreCase("Yes");
                    
                }
                
                System.out.println("\nPlease enter the bus fare: ");
                busFare = userInput.nextInt();
                
                System.out.println("\nPlease enter the walk time it takes to reach the bus stop (in Minutes): ");
                busWalkTime = userInput.nextInt();
                
                System.out.println("\nPlease enter the bus travel time (in Minutes): ");
                busTravelTime = userInput.nextInt();
                
                userInput.nextLine();
                
                System.out.println("\nPlease enter either 'Sunny' or 'Intense Weather: ");
                weather = userInput.nextLine();
                
                if (!weather.equalsIgnoreCase("Sunny") &&
                        !weather.equalsIgnoreCase("Intense Weather")) {
                    
                    throw new InvalidInputException("\nYou didn't input the correct weather!");
                }
                
                validInput = true;
                
            } catch (InputMismatchException | DateTimeParseException e) {
                System.out.println("\nThat input is invalid, please enter the correct value");
                userInput.nextLine(); // Clears invalid input
            } catch (InvalidInputException e) {
                
                System.out.println("\n" + e.getMessage());
            
            }
        }
        
        busObject = new Transportation("Bus " + (buses.size() + 1),
                busFare, busWalkTime, busTravelTime, weather, busService, deadline);
        
        buses.add(busObject);
        
        for (int i = 0; i < buses.size(); i++) {
            
            System.out.println("");
        
            System.out.println(buses.get(i));
        
        }
        
    }
    
    public void Train_Inputs () {
        boolean validInput = false;
        
        while (!validInput) {
            try {
                
                System.out.println("\nPlease insert the deadline for the train arrival (HH:mm): ");
                deadline = LocalTime.parse(userInput.nextLine());
                
                System.out.println("\nIs the train in service? Please type yes or no: ");
                String serviceAnswer = userInput.nextLine();
                
                if (!serviceAnswer.equalsIgnoreCase("Yes") &&
                        !serviceAnswer.equalsIgnoreCase("No")) {
                    
                    throw new InvalidInputException("\nPlease input yes or no!");
                    
                } else {
                    
                    trainService = serviceAnswer.equalsIgnoreCase("Yes");
                    
                }
                
                System.out.println("\nPlease enter the train fare: ");
                trainFare = userInput.nextDouble();
                
                System.out.println("\nPlease enter the walk time it takes to reach the station (in Minutes): ");
                trainWalkTime = userInput.nextDouble();
                
                System.out.println("\nPlease enter the train travel time (in Minutes): ");
                trainTravelTime = userInput.nextDouble();
                
                userInput.nextLine();
                
                System.out.println("\nPlease enter either 'Sunny' or 'Intense Weather: ");
                weather = userInput.nextLine();
                
                if (!weather.equalsIgnoreCase("Sunny") &&
                        !weather.equalsIgnoreCase("Intense Weather")) {
                    
                    throw new InvalidInputException("\nYou didn't input the correct weather!");
                    
                }
                
                validInput = true;
                
            } catch (InputMismatchException | DateTimeParseException e) {
                
                System.out.println("\nThat input is invalid, please enter the correct value");
                userInput.nextLine();
                
            } catch (InvalidInputException e) {
                
                System.out.println("\n" + e.getMessage());
                
            }
        }
        
        trainObject = new Transportation("Train " + (trains.size() + 1),
                trainFare, trainWalkTime, trainTravelTime, weather, trainService, deadline);
        
        trains.add(trainObject);
        
        for (int i = 0; i < trains.size(); i++) {
            
            System.out.println("");
            
            System.out.println(trains.get(i));
            
        }
        
    }
    
    public void Backup_Ride_Inputs() {
        boolean validInput = false;
        
        while (!validInput) {
            try {
                
                System.out.println("\nPlease insert the deadline for the backup ride arrival (HH:mm): ");
                deadline = LocalTime.parse(userInput.nextLine());
                
                System.out.println("\nIs the backup ride available? Please type yes or no: ");
                String serviceAnswer = userInput.nextLine();
                
                if (!serviceAnswer.equalsIgnoreCase("Yes") &&
                        !serviceAnswer.equalsIgnoreCase("No")) {
                    
                    throw new InvalidInputException("\nPlease input yes or no!");
                    
                } else {
                    
                    backupRideService = serviceAnswer.equalsIgnoreCase("Yes");
                    
                }
                
                System.out.println("\nPlease enter the backup ride fare: ");
                backupRideFare = userInput.nextDouble();
                
                System.out.println("\nPlease enter the walk time it takes to reach the backup ride (in Minutes): ");
                backupRideWalkTime = userInput.nextDouble();
                
                System.out.println("\nPlease enter the backup ride travel time (in Minutes): ");
                backupRideTravelTime = userInput.nextDouble();
                
                userInput.nextLine();
                
                System.out.println("\nPlease enter either 'Sunny' or 'Intense Weather': ");
                weather = userInput.nextLine();
                
                if (!weather.equalsIgnoreCase("Sunny") &&
                        !weather.equalsIgnoreCase("Intense Weather")) {
                    
                    throw new InvalidInputException("\nYou didn't input the correct weather!");
                    
                }
                
                validInput = true;
                
            } catch (InputMismatchException | DateTimeParseException e) {
                
                System.out.println("\nThat input is invalid, please enter the correct value");
                userInput.nextLine();
                
            } catch (InvalidInputException e) {
                
                System.out.println("\n" + e.getMessage());
                
            }
        }
        
        backupRideObject = new Transportation("Backup Ride " + (backupRides.size() + 1),
                backupRideFare, backupRideWalkTime, backupRideTravelTime, weather, backupRideService, deadline);
        
        backupRides.add(backupRideObject);
        
        for (int i = 0; i < backupRides.size(); i++) {
            
            System.out.println("");
            
            System.out.println(backupRides.get(i));
            
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
    
    public double getBusWalkTime() {
        
        return busWalkTime;
    }
    
    public double getTrainWalkTime() {
        
        return trainWalkTime;
    }
    
    public boolean getBusService() {
        
        return busService;
    }
    
    public boolean getTrainService() {
        
        return trainService;
    }
    
    public double getBusTravelTime() {
        
        return busTravelTime;
    }
    
    public double getTrainTravelTime() {
        
        return trainTravelTime;
    }
    
    public double getBusFare() {
        
        return busFare;
    }
    
    public double getTrainFare() {
        
        return trainFare;
    }
    
    public ArrayList<Transportation> getBuses() {
        
        return buses;
        
    }
    
    public ArrayList<Transportation> getTrains() {
        
        return trains;
        
    }
    
    public Scanner getUserInput() {
        
        return userInput;
        
    }
    
    public double getBackupRideWalkTime() {
        return backupRideWalkTime;
    }
    
    public boolean getBackupRideService() {
        return backupRideService;
    }
    
    public double getBackupRideTravelTime() {
        return backupRideTravelTime;
    }
    
    public double getBackupRideFare() {
        return backupRideFare;
    }
    
    public ArrayList<Transportation> getBackupRides() {
        return backupRides;
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