package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//This class evaluates whether any bus, train, or backupRide are even eligible to being compared
public class Bus_Train_Logic {
    
    private boolean busOp;
    
    private boolean busFareOp;
    
    private boolean busDeadlineOp;
    
    private boolean busWalkTimeOp;
    
    private boolean trainOp;
    
    private boolean trainFareOp;
    
    private boolean trainDeadlineOp;
    
    private boolean trainWalkTimeOp;
    
    private boolean busViable;
    
    private boolean trainViable;
    
    private boolean backupRideOp;
    
    private boolean backupRideFareOp;
    
    private boolean backupRideDeadlineOp;
    
    private boolean backupRideWalkTimeOp;
    
    private boolean backupRideViable;
    
    private double maxBadWeatherWalk = 10;
    
    //Stores all buses, trains, and backuprides that are viable for the
    //Recommendation_Algorithm class
    private ArrayList<Transportation> viableBuses = new ArrayList<>();
    
    private ArrayList<Transportation> viableTrains = new ArrayList<>();
    
    private ArrayList<Transportation> viableBackupRides = new ArrayList<>();
    
    private Bus_Train_Input bTInput;
    
    private Node busRule = Node.allOf(
            Node.var("B"), Node.var("F_bus"), Node.var("D_bus"));
    
    private Node trainRule = Node.allOf(
            Node.var("T"), Node.var("F_train"), Node.var("D_train"), Node.var("W"));
    
    private Node backupRule = Node.allOf(
            Node.var("R"), Node.var("F_backup"), Node.var("D_backup"));
    
    public static void main(String[] args) {
        
        
    }
    
    //Constructor that utilizes the user input from the Bus_Train_Input class
    public Bus_Train_Logic(Bus_Train_Input bTInput) {
        this.bTInput = bTInput;
    }
    
    public Bus_Train_Logic(boolean busOp) {
        this.busOp = busOp;
    }
    
    //
    public void evaluateLogic() {
        
        LogicNode trainEligible = LogicNode.allOf(
                LogicNode.var("T"), LogicNode.var("F_train"), LogicNode.var("D_train"),
                LogicNode.var("W"));
        
        viableBuses.clear();
        
        viableTrains.clear();
        
        viableBackupRides.clear();
        
        /*Uses the user input from the getbuses() method, uses stream to analyze the
        * transportation ArrayList, then checks if the serviceOperation in the ArrayList is true or false*/
        busOp = bTInput.getBuses().stream().anyMatch(bus -> bus.getServiceOperation());
        
        /*Uses the user input from the getbuses() method, uses stream to analyze the
         * transportation buses ArrayList, then checks if the Budget in the ArrayList is
         * atleast equal to or lower than the user's Budget*/
        busFareOp = bTInput.getBuses().stream()
                .anyMatch(bus -> bus.getFare() <= bTInput.getBudget());
        
        /*Uses the user input from the getbuses() method, uses stream to analyze the
         * transportation buses ArrayList, then checks what bus has the lowest walkTime*/
        busWalkTimeOp = bTInput.getBuses().stream()
                .anyMatch(bus -> bTInput.getBuses().stream()
                        .anyMatch(otherBus -> bus.getWalkTime() < otherBus.getWalkTime()));
        
        /*Uses the user input from the getTrains() method, uses stream to analyze the
         * transportation trains ArrayList, then checks if the serviceOperation is True or False*/
        trainOp = bTInput.getTrains().stream()
                .anyMatch(train -> train.getServiceOperation());
        
        /*Uses the user input from the getTrains() method, uses stream to analyze the transportation trains ArrayList,
        then checks if the train fare is equal to or lower than the User's Budget */
        trainFareOp = bTInput.getTrains().stream()
                .anyMatch(train -> train.getFare() <= bTInput.getBudget());
        
        /*Uses the user input from the getTrains() method, uses stream to analyze the transportation trains ArrayList,
         * then checks which Train's Walktime is the lowest*/
        trainWalkTimeOp = bTInput.getTrains().stream()
                .anyMatch(train -> bTInput.getTrains().stream()
                        .anyMatch(otherTrain -> train.getWalkTime() < otherTrain.getWalkTime()));
        
        /*Uses the user input from the getBackupride() method, uses stream to analyze the transportation backupRides
        ArrayList,then checks if the serviceOperation is true or false*/
        backupRideOp = bTInput.getBackupRides().stream()
                .anyMatch(backupRide -> backupRide.getServiceOperation());
        
        /*Uses the user input from the getBackupride() method, uses stream to analyze the transportation backupRides
        ArrayList,then checks if the backupRide's fare is lower than the user's inputted budget*/
        backupRideFareOp = bTInput.getBackupRides().stream()
                .anyMatch(backupRide -> backupRide.getFare() <= bTInput.getBudget());
        
        /*Uses the user input from the getBackupride() method, uses stream to analyze the transportation backupRides
        ArrayList,then checks what backupride has the lowest walktime*/
        backupRideWalkTimeOp = bTInput.getBackupRides().stream()
                .anyMatch(ride -> bTInput.getBackupRides().stream()
                        .anyMatch(otherRide -> ride.getWalkTime() < otherRide.getWalkTime()));
        
        //Checks each individual bus to see if they're valid for recommendation_algorithm
        for (Transportation bus : bTInput.getBuses()) {
            
            Map<String, Boolean> env = new HashMap<>();
            env.put("B", bus.getServiceOperation());
            env.put("F_bus", bus.getFare() <= bTInput.getBudget());
            env.put("D_bus", bTInput.getCurrentTime()
                    .plusMinutes((long) Math.ceil(bus.getWalkTime() + bus.getTravelTime()))
                    .isBefore(bus.getDeadline()));
            
            if (BUS_RULE.eval(env)) {
                viableBuses.add(bus);
            }
        }

        //Checks each individual train to see if they're valid for recommendation_algorithm
        for (Transportation train : bTInput.getTrains()) {
            
            Map<String, Boolean> env = new HashMap<>();
            env.put("T", train.getServiceOperation());
            env.put("F_train", train.getFare() <= bTInput.getBudget());
            env.put("D_bus", bTInput.getCurrentTime()
                    .plusMinutes((long) Math.ceil(train.getWalkTime() + train.getTravelTime()))
                    .isBefore(train.getDeadline()));
            
            if (TRAIN_RULE.eval(env)) {
                viableTrains.add(train);
            }
        }
        //Checks each individual backupRide to see if they're valid for recommendation_algorithm
        for (Transportation backupRide : bTInput.getBackupRides()) {
            Map<String, Boolean> env = new HashMap<>();
            env.put("R", backupRide.getServiceOperation());
            env.put("F_bus", backupRide.getFare() <= bTInput.getBudget());
            env.put("D_bus", bTInput.getCurrentTime()
                    .plusMinutes((long) Math.ceil(backupRide.getWalkTime() + backupRide.getTravelTime()))
                    .isBefore(backupRide.getDeadline()));
            
            if (BACKUP_RULE.eval(env)) {
                viableBuses.add(backupRide);
            }
        }
        
        //Checks to see if each arraylist has zero elements or not
        busViable = !viableBuses.isEmpty();
        
        trainViable = !viableTrains.isEmpty();
        
        backupRideViable = !viableBackupRides.isEmpty();
        
        /*Each of these goes through their respective arraylists, gets the currentTime by converting it to long
        // then adds both walking and traveltime together, then compaes to see if they are befoe the deadline of
        the vehicle*/
        busDeadlineOp = bTInput.getBuses().stream().anyMatch(bus ->
                bTInput.getCurrentTime().plusMinutes((long) Math.ceil(bus.getWalkTime()
                        + bus.getTravelTime())).isBefore(bus.getDeadline()));
        
        trainDeadlineOp = bTInput.getTrains().stream().anyMatch(train ->
                bTInput.getCurrentTime().plusMinutes((long) Math.ceil(train.getWalkTime()
                        + train.getTravelTime())).isBefore(train.getDeadline()));
        
        backupRideDeadlineOp = bTInput.getBackupRides().stream().anyMatch(backupRide ->
                bTInput.getCurrentTime().plusMinutes((long) Math.ceil(backupRide.getWalkTime()
                        + backupRide.getTravelTime())).isBefore(backupRide.getDeadline()));
    }
    
    //All Class Getters
    public boolean getBusOp() {
        
        return busOp;
        
    }
    
    public boolean getBusFareOp() {
        
        return busFareOp;
        
    }
    
    public boolean getBusDeadlineOp() {
        
        return busDeadlineOp;
        
    }
    
    public boolean getBusWalkTimeOp() {
        
        return busWalkTimeOp;
        
    }
    
    public boolean getBusViable() {
        
        return busViable;
        
    }
    
    // TRAIN GETTERS
    
    public boolean getTrainOp() {
        
        return trainOp;
        
    }
    
    public boolean getTrainFareOp() {
        
        return trainFareOp;
        
    }
    
    public boolean getTrainDeadlineOp() {
        
        return trainDeadlineOp;
        
    }
    
    public boolean getTrainWalkTimeOp() {
        
        return trainWalkTimeOp;
        
    }
    
    public boolean getBackupRideOp() {
        return backupRideOp;
    }
    
    public boolean getBackupRideFareOp() {
        return backupRideFareOp;
    }
    
    public boolean getBackupRideDeadlineOp() {
        return backupRideDeadlineOp;
    }
    
    public boolean getBackupRideWalkTimeOp() {
        return backupRideWalkTimeOp;
    }
    
    public boolean getBackupRideViable() {
        return backupRideViable;
    }
    
    public ArrayList<Transportation> getViableBackupRides() {
        return viableBackupRides;
    }
    
    public boolean getTrainViable() {
        return trainViable;
    }
    
    public ArrayList<Transportation> getViableBuses() {
       
        return viableBuses;
        
    }
    
    public ArrayList<Transportation> getViableTrains() {
        
        return viableTrains;
        
    }
    
    public LogicNode getBusRule() {
        return BUS_RULE;
    }
    
    public LogicNode getTrainRule() {
        return TRAIN_RULE;
    }
    
    public LogicNode getBackupRule() {
        return BACKUP_RULE;
    }
    
/*busViable = busOp && busFareOp && busDeadlineOp;
        
        trainViable = trainOp && trainFareOp && trainDeadlineOp && trainWalkTimeOp;
        
        backupRideViable = backupRideOp && backupRideFareOp &&
                backupRideDeadlineOp && backupRideWalkTimeOp;*/
    
    
}