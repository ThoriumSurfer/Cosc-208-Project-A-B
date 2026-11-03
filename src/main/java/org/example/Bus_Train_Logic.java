package org.example;

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
    
    public static void main(String[] args) {
        
        
    }
    
    private Bus_Train_Input bTInput;
    
    public Bus_Train_Logic(Bus_Train_Input bTInput) {
        this.bTInput = bTInput;
    }
    
    public Bus_Train_Logic(boolean busOp) {
        this.busOp = busOp;
    }
    
    public void evaluatePropositions() {
        
        busOp = bTInput.getBusService();
        
        busFareOp = bTInput.getBusFare() <= bTInput.getBudget();
        
        busDeadlineOp = bTInput.getCurrentTime().plusMinutes(bTInput.getBusTravelTime())
                .isBefore(bTInput.getDeadline());
        
        busWalkTimeOp = !bTInput.getWeather().equalsIgnoreCase("Severe");
        
        busViable = busOp && busFareOp && busDeadlineOp && busWalkTimeOp;
        
        trainOp = bTInput.getTrainService();
        
        trainFareOp = bTInput.getTrainFare() <= bTInput.getBudget();
        
        trainDeadlineOp = bTInput.getCurrentTime().plusMinutes(bTInput.getTrainTravelTime())
                .isBefore(bTInput.getDeadline());
        
        trainWalkTimeOp = !bTInput.getWeather().equalsIgnoreCase("Severe");
        
        trainViable = trainOp && trainFareOp && trainDeadlineOp && trainWalkTimeOp;
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

    
}