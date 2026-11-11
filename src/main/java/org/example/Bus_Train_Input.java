package org.example;

//import org.example.InvalidWeatherException;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;



/* This is where the inputs for each value for Bus/Train/and Backupride is Evaluated. Deadline, currentTIme,
budget weather, walktime's for each, traveltime for each, fares for each, and if each of them are even
in service in the first place.
 */
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
    
    /*These Transportation Class Arraylists will store the values inputted from the user
    as many tiems as they need*/
    
    private ArrayList<Transportation> backupRides = new ArrayList<>();
    
    private Transportation backupRideObject;
    
    private ArrayList<Transportation> buses = new ArrayList<>();
    
    private ArrayList<Transportation> trains = new ArrayList<>();
    
    private Transportation busObject;
    
    private Transportation trainObject;
    
    public static void main(String[] args) {

    }
    
    //Constructor for all variables in this Class
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
    
    //Records user's Budget once at the start
    public void Budget_Input() {
        
        System.out.println("\nEnter your budget: ");
        
        budget = userInput.nextDouble();
        
        userInput.nextLine();
    }
    
    public void Departure_Input() {
        
        while (true) {
            
            System.out.println("\nEnter your departure time (HH:mm), or press Enter to use the current time: ");
            
            String answer = userInput.nextLine().trim();
            
            if (answer.isEmpty()) {
                
                currentTime = LocalTime.now().withSecond(0).withNano(0);
                
                break;
            }
            
            try {
                
                currentTime = LocalTime.parse(answer);
                
                break;
            
            } catch (DateTimeParseException e) {
                
                System.out.println("\nThat time is invalid, please use HH:mm (for example 08:15).");
            
            }
        }
        
        System.out.println("Using departure time: " + currentTime);
        
    }
    
    public void Deadline_Input() {
        
        boolean valid = false;
        
        while (!valid) {
            
            try {
                
                System.out.println("\nPlease enter your arrival deadline (HH:mm): ");
                
                deadline = LocalTime.parse(userInput.nextLine().trim());
                
                valid = true;
                
            } catch (DateTimeParseException e) {
                
                System.out.println("\nThat time is invalid, please use HH:mm (for example 09:00).");
                
            }
        }
    }
    
    /*This Method is what takes the user's inputs for all buses. Deadline, Serviceanswer, busfare,
    * busWalkTIme, busTravelTime, weather, and creates busObjects */
    public void Bus_Inputs ()
    {
        boolean validInput = false;
        
        while (!validInput) {
            try {
                
                System.out.println("\nIs the bus in service? Please type yes or no: ");
                String serviceAnswer = userInput.nextLine();
                //Determines if they inputted yes or no, if user inputs neither then throw
                //custom exception
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
                
                //Determines if they inputted yes or no, if user inputs neither then throw
                //custom exception
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
        
        //This bus object is created to store all of these values so it can be added all at once to the
        //buses arraylist
        busObject = new Transportation("Bus " + (buses.size() + 1),
                busFare, busWalkTime, busTravelTime, weather, busService, deadline);
        
        buses.add(busObject);
        
        for (int i = 0; i < buses.size(); i++) {
            
            System.out.println("");
        
            System.out.println(buses.get(i));
        
        }
        
    }
    
    /*This Method is what takes the user's inputs for all trains. Deadline, Serviceanswer, trainfare,
     * trainWalkTIme, trainTravelTime, weather, and creates trainObjects */
    public void Train_Inputs () {
        boolean validInput = false;
        
        while (!validInput) {
            try {
                
                System.out.println("\nIs the train in service? Please type yes or no: ");
                String serviceAnswer = userInput.nextLine();
                //Determines if they inputted yes or no, if user inputs neither then throw
                //custom exception
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
                //Determines if they inputted yes or no, if user inputs neither then throw
                //custom exception
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
        //This train object is created to store all of these values so it can be added all at once to the
        //trains arraylist
        trainObject = new Transportation("Train " + (trains.size() + 1),
                trainFare, trainWalkTime, trainTravelTime, weather, trainService, deadline);
        
        trains.add(trainObject);
        
        for (int i = 0; i < trains.size(); i++) {
            
            System.out.println("");
            
            System.out.println(trains.get(i));
            
        }
        
    }
    
    /*This Method is what takes the user's inputs for all backuprides. Deadline, Serviceanswer,
    backupRideFare, backupRideWalkTIme, backupRideTravelTime, weather,
    and creates backupRideObjects */
    public void Backup_Ride_Inputs() {
        boolean validInput = false;
        
        while (!validInput) {
            try {
                
                System.out.println("\nIs the backup ride available? Please type yes or no: ");
                String serviceAnswer = userInput.nextLine();
                //Determines if they inputted yes or no, if user inputs neither then throw
                //custom exception
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
                //Determines if they inputted yes or no, if user inputs neither then throw
                //custom exception
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
        //This backupRide object is created to store all of these values so it can be
        // added all at once to the backupRide arraylist
        backupRideObject = new Transportation("Backup Ride " + (backupRides.size() + 1),
                backupRideFare, backupRideWalkTime, backupRideTravelTime, weather, backupRideService, deadline);
        
        backupRides.add(backupRideObject);
        
        for (int i = 0; i < backupRides.size(); i++) {
            
            System.out.println("");
            
            System.out.println(backupRides.get(i));
            
        }
    }
    
    //All class Getters
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