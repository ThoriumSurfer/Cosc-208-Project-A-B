package org.example;
import org.example.InvalidInputException;
//import org.example.InvalidWeatherException;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
Cosc 208 Project A and B
This Project is Meant to take the inout of the user that determines whether the
 Makail Casey
 */

public class Main {
    
    public static void main(String[] args) {
        
        Scanner userInput = new Scanner(System.in);
        
        System.out.println("Hello and welcome!");
        
        //String busOrTrain = userInput.nextLine();
        // Create the input object
        Bus_Train_Input input = new Bus_Train_Input();
        
        int userOptions = 0;
        
        while(userOptions == 0) {
           
            try {
                System.out.println("\nDo you want to add a bus? yes/no: ");
                
                String endLoop = yes_Or_No(userInput);
                
                input.Budget_Input();
                
                while (endLoop.equalsIgnoreCase("yes")) {
                    
                    input.Bus_Inputs();
                    
                    System.out.println("\nDo you want to add another bus? yes/no: ");
                    
                    endLoop = yes_Or_No(userInput);
                    
                }
                
                System.out.println("\nDo you want to add a train? yes/no: ");
                
                endLoop = yes_Or_No(userInput);
                
                while (endLoop.equalsIgnoreCase("yes")) {
                    
                    input.Train_Inputs();
                    
                    System.out.println("\nDo you want to add another train? yes/no: ");
                    
                    endLoop = yes_Or_No(userInput);
                    
                }
                
                System.out.println("\nDo you want to add a backup ride? Please type yes or no: ");
                
                endLoop = yes_Or_No(userInput);
                
                while (endLoop.equalsIgnoreCase("yes")) {
                    
                    input.Backup_Ride_Inputs();
                    
                    System.out.println("\nDo you want to add another backup ride? Please type yes or no: ");
                    
                    endLoop = yes_Or_No(userInput);
                    
                }
                
                userOptions += 1;
                
            } catch (InputMismatchException e){
                
                System.out.println("\nPlease type yes or no!");
                userInput.nextLine();
                
            } catch (InvalidInputException e) {
                
                System.out.println("\n" + e.getMessage());
                
            }
        }
        
        // Create the logic object and pass it the input
        Bus_Train_Logic logic = new Bus_Train_Logic(input);
        
        // Evaluate the bus and train propositions
        logic.evaluateLogic();
        
        Recommendation_Algorithm recommendation = new Recommendation_Algorithm(input, logic);
        
        recommendation.Determine_Priority();
        
    }
    
    public static String yes_Or_No(Scanner userInput) throws InvalidInputException {
        String answer = userInput.nextLine();
        
        if (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            throw new InvalidInputException("\nPlease type yes or no!");
        }
        
        return answer;
    }
    
}