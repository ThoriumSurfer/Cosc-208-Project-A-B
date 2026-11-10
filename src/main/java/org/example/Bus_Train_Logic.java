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
        
        viableBuses.clear();
        
        viableTrains.clear();
        
        viableBackupRides.clear();
        
        busOp = bTInput.getBuses().stream().anyMatch(bus -> bus.getServiceOperation());
        
        busFareOp = bTInput.getBuses().stream()
                .anyMatch(bus -> bus.getFare() <= bTInput.getBudget());
        
        busWalkTimeOp = bTInput.getBuses().stream()
                .anyMatch(bus -> bTInput.getBuses().stream()
                        .anyMatch(otherBus -> bus.getWalkTime() < otherBus.getWalkTime()));
        
        trainOp = bTInput.getTrains().stream()
                .anyMatch(train -> train.getServiceOperation());
        
        trainFareOp = bTInput.getTrains().stream()
                .anyMatch(train -> train.getFare() <= bTInput.getBudget());
        
        trainWalkTimeOp = bTInput.getTrains().stream()
                .anyMatch(train -> bTInput.getTrains().stream()
                        .anyMatch(otherTrain -> train.getWalkTime() < otherTrain.getWalkTime()));
        
        backupRideOp = bTInput.getBackupRides().stream()
                .anyMatch(backupRide -> backupRide.getServiceOperation());
        
        backupRideFareOp = bTInput.getBackupRides().stream()
                .anyMatch(backupRide -> backupRide.getFare() <= bTInput.getBudget());
        
        backupRideWalkTimeOp = bTInput.getBackupRides().stream()
                .anyMatch(ride -> bTInput.getBackupRides().stream()
                        .anyMatch(otherRide -> ride.getWalkTime() < otherRide.getWalkTime()));
        
        for (Transportation bus : bTInput.getBuses()) {
            
            boolean serviceValid = bus.getServiceOperation();
            
            boolean fareValid = bus.getFare() <= bTInput.getBudget();
            
            boolean deadlineValid = bTInput.getCurrentTime()
                    .plusMinutes((long) Math.ceil(bus.getWalkTime() + bus.getTravelTime()))
                    .isBefore(bus.getDeadline());
            
            if (serviceValid && fareValid && deadlineValid) {
                viableBuses.add(bus);
            }
        }

// Check individual trains
        for (Transportation train : bTInput.getTrains()) {
            
            boolean fareValid = train.getFare() <= bTInput.getBudget();
            
            boolean deadlineValid = bTInput.getCurrentTime()
                    .plusMinutes((long) Math.ceil(train.getWalkTime() + train.getTravelTime()))
                    .isBefore(train.getDeadline());
            
            boolean serviceValid = train.getServiceOperation();
            
            if (serviceValid && fareValid && deadlineValid) {
                viableTrains.add(train);
            }
        }
        
        for (Transportation backupRide : bTInput.getBackupRides()) {
            
            boolean serviceValid = backupRide.getServiceOperation();
            
            boolean fareValid = backupRide.getFare() <= bTInput.getBudget();
            
            boolean deadlineValid = bTInput.getCurrentTime()
                    .plusMinutes((long) Math.ceil(backupRide.getWalkTime() + backupRide.getTravelTime()))
                    .isBefore(backupRide.getDeadline());
            
            if (serviceValid && fareValid && deadlineValid) {
                viableBackupRides.add(backupRide);
            }
        }
        
        busViable = !viableBuses.isEmpty();
        
        trainViable = !viableTrains.isEmpty();
        
        backupRideViable = !viableBackupRides.isEmpty();
        
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
    
/*busViable = busOp && busFareOp && busDeadlineOp;
        
        trainViable = trainOp && trainFareOp && trainDeadlineOp && trainWalkTimeOp;
        
        backupRideViable = backupRideOp && backupRideFareOp &&
                backupRideDeadlineOp && backupRideWalkTimeOp;*/
    
    
}