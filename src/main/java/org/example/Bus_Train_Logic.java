package org.example;

import java.util.ArrayList;
import java.time.LocalTime;
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
    
    private double maxBadWeatherWalk = 20;
    
    //Stores all buses, trains, and backuprides that are viable for the
    //Recommendation_Algorithm class
    private ArrayList<Transportation> viableBuses = new ArrayList<>();
    
    private ArrayList<Transportation> viableTrains = new ArrayList<>();
    
    private ArrayList<Transportation> viableBackupRides = new ArrayList<>();
    
    //Stores the true/false value of every proposition for each vehicle so results can be explained later
    private HashMap<Transportation, Map<String, Boolean>> evaluations = new HashMap<>();
    
    private Bus_Train_Input bTInput;
    
    //Each rule is an expression tree of AND nodes. A vehicle is eligible only if every proposition is true:
    //service operating, fare within budget, arrives before the deadline, and walking conditions acceptable
    private LogicNode busRule = LogicNode.allOf(
            LogicNode.var("B"), LogicNode.var("F_bus"), LogicNode.var("D_bus"),
            LogicNode.var("W"));
    
    private LogicNode trainRule = LogicNode.allOf(
            LogicNode.var("T"), LogicNode.var("F_train"), LogicNode.var("D_train"),
            LogicNode.var("W"));
    
    private LogicNode backupRule = LogicNode.allOf(
            LogicNode.var("R"), LogicNode.var("F_backup"), LogicNode.var("D_backup"),
            LogicNode.var("W"));
    
    public static void main(String[] args) {
        
        
    }
    
    //Constructor that utilizes the user input from the Bus_Train_Input class
    public Bus_Train_Logic(Bus_Train_Input bTInput) {
        this.bTInput = bTInput;
    }
    
    public Bus_Train_Logic(boolean busOp) {
        this.busOp = busOp;
    }
    
    public void explainEligibility() {
        
        explainResults(bTInput.getBuses(), busRule);
        
        explainResults(bTInput.getTrains(), trainRule);
        
        explainResults(bTInput.getBackupRides(), backupRule);
        
    }
    
    private void explainResults(ArrayList<Transportation> vehicles, LogicNode rule) {
        
        for (Transportation vehicle : vehicles) {
            
            Map<String, Boolean> env = evaluations.get(vehicle);
            
            if (rule.eval(env)) {
                
                System.out.println("\n" + vehicle.getVehicleName() + " is eligible because:");
                
                for (String proposition : rule.variables()) {
                    
                    System.out.println(" - " + describe(proposition, vehicle, true));
                    
                }
                
            } else {
                
                System.out.println("\n" + vehicle.getVehicleName() + " is NOT eligible because:");
                
                for (String proposition : rule.failedPropositions(env)) {
                    
                    System.out.println(" - " + describe(proposition, vehicle, false));
                    
                }
            }
        }
    }
    
    private String describe(String proposition, Transportation vehicle, boolean passed) {
        
        if (proposition.startsWith("F")) {
            
            return "the fare (" + vehicle.getFare() + ") is " + (passed ? "within" : "over")
                    + " your budget (" + bTInput.getBudget() + ")";
            
        } else if (proposition.startsWith("D")) {
            
            LocalTime arrival = bTInput.getCurrentTime()
                    .plusMinutes((long) Math.ceil(vehicle.getWalkTime() + vehicle.getTravelTime()));
            
            return "you would arrive at " + arrival + ", which is " + (passed ? "before" : "not before")
                    + " the deadline of " + vehicle.getDeadline();
            
        } else if (proposition.equals("W")) {
            
            return "walking conditions are " + (passed ? "" : "not ") + "acceptable ("
                    + vehicle.getWeather() + ", " + vehicle.getWalkTime() + " minute walk)";
            
        } else {
            
            return "the service is " + (passed ? "" : "not ") + "operating";
            
        }
    }
    
    public void printKnowledgeGraph(Transportation bestTransportation) {
        
        KnowledgeGraph graph = new KnowledgeGraph();
        
        graph.addEdge("Maya", "needs to reach", "Campus");
        
        graph.addEdge("Maya", "has budget", "Budget " + bTInput.getBudget());
        
        graph.addEdge("Maya", "must arrive before", "Deadline " + bTInput.getDeadline());
        
        addVehiclesToGraph(graph, bTInput.getBuses(), busRule, bestTransportation);
        
        addVehiclesToGraph(graph, bTInput.getTrains(), trainRule, bestTransportation);
        
        addVehiclesToGraph(graph, bTInput.getBackupRides(), backupRule, bestTransportation);
        
        graph.print();
        
    }
    
    private void addVehiclesToGraph(KnowledgeGraph graph, ArrayList<Transportation> vehicles,
                                    LogicNode rule, Transportation bestTransportation) {
        
        for (Transportation vehicle : vehicles) {
            
            Map<String, Boolean> env = evaluations.get(vehicle);
            
            String name = vehicle.getVehicleName();
            
            String fare = "Fare " + vehicle.getFare();
            
            LocalTime arrival = bTInput.getCurrentTime()
                    .plusMinutes((long) Math.ceil(vehicle.getWalkTime() + vehicle.getTravelTime()));
            
            graph.addEdge("Maya", "can take", name);
            
            graph.addEdge(name, "goes to", "Campus");
            
            graph.addEdge(name, "service is", vehicle.getServiceOperation() ? "Operating" : "Not operating");
            
            graph.addEdge(name, "costs", fare);
            
            graph.addEdge(fare, vehicle.getFare() <= bTInput.getBudget() ? "is within" : "is over",
                    "Budget " + bTInput.getBudget());
            
            graph.addEdge(name, "affected by", "Weather " + vehicle.getWeather());
            
            graph.addEdge(name, "arrives at", "Arrival " + arrival);
            
            //Reads the deadline answer (D_bus, D_train, or D_backup) that was stored for this vehicle
            boolean meetsDeadline = false;
            
            for (String proposition : env.keySet()) {
                
                if (proposition.startsWith("D")) {
                    
                    meetsDeadline = env.get(proposition);
                    
                }
            }
            
            graph.addEdge("Arrival " + arrival, meetsDeadline ? "is before" : "is not before",
                    "Deadline " + vehicle.getDeadline());
            
            graph.addEdge(name, rule.eval(env) ? "is" : "is not", "Eligible");
            
            if (vehicle == bestTransportation) {
                
                graph.addEdge("Maya", "is recommended", name);
                
            }
        }
    }
    
    //
    public void evaluateLogic() {
        
        LogicNode trainEligible = LogicNode.allOf(
                LogicNode.var("T"), LogicNode.var("F_train"), LogicNode.var("D_train"),
                LogicNode.var("W"));
        
        viableBuses.clear();
        
        viableTrains.clear();
        
        viableBackupRides.clear();
        
        evaluations.clear();
        
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
            
            env.put("W", bus.getWeather().equalsIgnoreCase("Sunny")
                    || bus.getWalkTime() <= maxBadWeatherWalk);
            
            evaluations.put(bus, env);
            
            if (busRule.eval(env)) {
                
                viableBuses.add(bus);
                
            }
        }

        //Checks each individual train to see if they're valid for recommendation_algorithm
        for (Transportation train : bTInput.getTrains()) {
            
            Map<String, Boolean> env = new HashMap<>();
            
            env.put("T", train.getServiceOperation());
            
            env.put("F_train", train.getFare() <= bTInput.getBudget());
            
            env.put("D_train", bTInput.getCurrentTime()
                    .plusMinutes((long) Math.ceil(train.getWalkTime() + train.getTravelTime()))
                    .isBefore(train.getDeadline()));
            
            env.put("W", train.getWeather().equalsIgnoreCase("Sunny")
                    || train.getWalkTime() <= maxBadWeatherWalk);
            
            evaluations.put(train, env);
            
            if (trainRule.eval(env)) {
                
                viableTrains.add(train);
                
            }
        }
        //Checks each individual backupRide to see if they're valid for recommendation_algorithm
        for (Transportation backupRide : bTInput.getBackupRides()) {
            
            Map<String, Boolean> env = new HashMap<>();
            
            env.put("R", backupRide.getServiceOperation());
            
            env.put("F_backup", backupRide.getFare() <= bTInput.getBudget());
            
            env.put("D_backup", bTInput.getCurrentTime()
                    .plusMinutes((long) Math.ceil(backupRide.getWalkTime() + backupRide.getTravelTime()))
                    .isBefore(backupRide.getDeadline()));
            
            env.put("W", backupRide.getWeather().equalsIgnoreCase("Sunny")
                    || backupRide.getWalkTime() <= maxBadWeatherWalk);
            
            evaluations.put(backupRide, env);
            
            if (backupRule.eval(env)) {
                
                viableBackupRides.add(backupRide);
                
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
    
    public void printDecisionTree(Transportation bestTransportation) {
        
        System.out.println("\nDecision tree:");
        
        decisionPath(bTInput.getBuses(), busRule, bestTransportation);
        
        decisionPath(bTInput.getTrains(), trainRule, bestTransportation);
        
        decisionPath(bTInput.getBackupRides(), backupRule, bestTransportation);
        
        System.out.println("\nFinal step: which eligible option has the highest weighted score?");
        
        if (bestTransportation == null) {
            
            System.out.println("  -> No eligible option, so there is no recommendation");
            
        } else {
            
            System.out.println("  -> " + bestTransportation.getVehicleName() + " (score " + bestTransportation.getTotalScore()
                    + ") is RECOMMENDED");
            
        }
        
    }
    
    private void decisionPath(ArrayList<Transportation> vehicles, LogicNode rule,
                              Transportation bestTransportation) {
        
        for (Transportation vehicle : vehicles) {
            
            Map<String, Boolean> env = evaluations.get(vehicle);
            
            System.out.println("\n" + vehicle.getVehicleName());
            
            boolean eligible = true;
            
            //Asks each question in the same order as the rule. Stops at the first NO branch.
            for (String proposition : rule.variables()) {
                
                boolean answer = env.get(proposition);
                
                System.out.println("   " + questions(proposition, vehicle) + " " + (answer ? "YES" : "NO"));
                
                if (!answer) {
                    
                    System.out.println("   " + "  -> NOT eligible");
                    
                    eligible = false;
                    
                    break;
                }
                
            }
            
            if (eligible) {
                
                System.out.println("    " + "-> ELIGIBLE" + (vehicle == bestTransportation ? " (RECOMMENDED)" : ""));
                
            }
        }
    }
    
    private String questions(String proposition, Transportation vehicle) {
        
        if (proposition.startsWith("F")) {
            
            return "Is the fare within budget (" + vehicle.getFare() + " vs " + bTInput.getBudget() + ")?";
            
        } else if (proposition.startsWith("D")) {
            
            LocalTime arrival = bTInput.getCurrentTime()
                    .plusMinutes((long) Math.ceil(vehicle.getWalkTime() + vehicle.getTravelTime()));
            
            return "Will arrival (" + arrival + ") meet the deadline (" + vehicle.getDeadline() + ")?";
            
        } else if (proposition.equals("W")) {
            
            return "Are walking conditions acceptable (" + vehicle.getWeather() + ", "
                    + vehicle.getWalkTime() + " minute walk)?";
            
        } else {
            
            return "Is the service operating?";
            
        }
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
        return busRule;
    }
    
    public LogicNode getTrainRule() {
        return trainRule;
    }
    
    public LogicNode getBackupRule() {
        return backupRule;
    }
    
/*busViable = busOp && busFareOp && busDeadlineOp;
        
        trainViable = trainOp && trainFareOp && trainDeadlineOp && trainWalkTimeOp;
        
        backupRideViable = backupRideOp && backupRideFareOp &&
                backupRideDeadlineOp && backupRideWalkTimeOp;*/
    
    
}