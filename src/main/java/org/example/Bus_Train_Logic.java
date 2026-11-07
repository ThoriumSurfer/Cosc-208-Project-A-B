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
        
        busDeadlineOp = bTInput.getCurrentTime().plusMinutes((long) bTInput.getBusTravelTime())
                .isBefore(bTInput.getDeadline());
        
        busWalkTimeOp = !bTInput.getWeather().equalsIgnoreCase("Severe");
        
        busViable = busOp && busFareOp && busDeadlineOp && busWalkTimeOp;
        
        trainOp = bTInput.getTrainService();
        
        trainFareOp = bTInput.getTrainFare() <= bTInput.getBudget();
        
        trainDeadlineOp = bTInput.getCurrentTime().plusMinutes((long) bTInput.getTrainTravelTime())
                .isBefore(bTInput.getDeadline());
        
        trainWalkTimeOp = !bTInput.getWeather().equalsIgnoreCase("Severe");
        
        trainViable = trainOp && trainFareOp && trainDeadlineOp && trainWalkTimeOp;
        
        for (Transportation bus : bTInput.getBuses()) {
            
            boolean fareValid = bus.getFare() <= bTInput.getBudget();
            
            boolean deadlineValid = bTInput.getCurrentTime()
                    .plusMinutes((long) bus.getTravelTime())
                    .isBefore(bTInput.getDeadline());
            
            boolean weatherValid = !bus.getWeather().equalsIgnoreCase("Severe");
            
            if (busOp && fareValid && deadlineValid && weatherValid) {
                viableBuses.add(bus);
            }
        }

// Check individual trains
        for (Transportation train : bTInput.getTrains()) {
            
            boolean fareValid = train.getFare() <= bTInput.getBudget();
            
            boolean deadlineValid = bTInput.getCurrentTime()
                    .plusMinutes((long) train.getTravelTime())
                    .isBefore(bTInput.getDeadline());
            
            boolean weatherValid = !train.getWeather().equalsIgnoreCase("Severe");
            
            if (trainOp && fareValid && deadlineValid && weatherValid) {
                viableTrains.add(train);
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