package org.example;

import java.util.ArrayList;

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
    
    private ArrayList<Transportation> viableBackupRides = new ArrayList<>();
    
    private ArrayList<Transportation> viableBuses = new ArrayList<>();
    
    private ArrayList<Transportation> viableTrains = new ArrayList<>();
    
    public static void main(String[] args) {
        
        
    }
    
    private Bus_Train_Input bTInput;
    
    public Bus_Train_Logic(Bus_Train_Input bTInput) {
        this.bTInput = bTInput;
    }
    
    public Bus_Train_Logic(boolean busOp) {
        this.busOp = busOp;
    }
    
    public void evaluateLogic() {
        
        busOp = bTInput.getBusService();
        
        busFareOp = bTInput.getBusFare() <= bTInput.getBudget();
        
        busDeadlineOp = bTInput.getCurrentTime()
                .plusMinutes((long) Math.ceil(bTInput.getBusWalkTime() + bTInput.getBusTravelTime()))
                .isBefore(bTInput.getDeadline());
        
        busWalkTimeOp = !bTInput.getWeather().equalsIgnoreCase("Severe");
        
        busViable = busOp && busFareOp && busDeadlineOp;
        
        trainOp = bTInput.getTrainService();
        
        trainFareOp = bTInput.getTrainFare() <= bTInput.getBudget();
        
        trainDeadlineOp = bTInput.getCurrentTime()
                .plusMinutes((long) Math.ceil(bTInput.getTrainWalkTime() + bTInput.getTrainTravelTime()))
                .isBefore(bTInput.getDeadline());
        
        trainWalkTimeOp = !bTInput.getWeather().equalsIgnoreCase("Severe");
        
        trainViable = trainOp && trainFareOp && trainDeadlineOp && trainWalkTimeOp;
        
        backupRideOp = bTInput.getBackupRideService();
        
        backupRideFareOp = bTInput.getBackupRideFare() <= bTInput.getBudget();
        
        backupRideDeadlineOp = bTInput.getCurrentTime()
                .plusMinutes((long) bTInput.getBackupRideTravelTime())
                .isBefore(bTInput.getDeadline());
        
        backupRideWalkTimeOp = !bTInput.getWeather().equalsIgnoreCase("Severe");
        
        backupRideViable = backupRideOp && backupRideFareOp &&
                backupRideDeadlineOp && backupRideWalkTimeOp;
        
        for (Transportation bus : bTInput.getBuses()) {
            
            boolean fareValid = bus.getFare() <= bTInput.getBudget();
            
            boolean deadlineValid = bTInput.getCurrentTime()
                    .plusMinutes((long) Math.ceil(bus.getWalkTime() + bus.getTravelTime()))
                    .isBefore(bTInput.getDeadline());
            
            if (busOp && fareValid && deadlineValid) {
                viableBuses.add(bus);
            }
        }

// Check individual trains
        for (Transportation train : bTInput.getTrains()) {
            
            boolean fareValid = train.getFare() <= bTInput.getBudget();
            
            boolean deadlineValid = bTInput.getCurrentTime()
                    .plusMinutes((long) Math.ceil(train.getWalkTime() + train.getTravelTime()))
                    .isBefore(bTInput.getDeadline());
            
            boolean weatherValid = train.getWeather().equalsIgnoreCase("Sunny");
            
            if (trainOp && fareValid && deadlineValid && weatherValid) {
                viableTrains.add(train);
            }
        }
        
        for (Transportation backupRide : bTInput.getBackupRides()) {
            
            boolean fareValid = backupRide.getFare() <= bTInput.getBudget();
            
            boolean deadlineValid = bTInput.getCurrentTime()
                    .plusMinutes((long) backupRide.getTravelTime())
                    .isBefore(bTInput.getDeadline());
            
            boolean weatherValid = !backupRide.getWeather().equalsIgnoreCase("Severe");
            
            if (backupRideOp && fareValid && deadlineValid && weatherValid) {
                viableBackupRides.add(backupRide);
            }
        }
    }
    
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

    
}