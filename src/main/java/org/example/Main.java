package org.example;
import org.example.InvalidInputException;
//import org.example.InvalidWeatherException;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
Cosc 208 Project A and B
This Project is Meant to take the inout of the user that determines the best transportation method
for the user
 Makail Casey
 */

public class Main {
    
    /* This main will ask the user if they want to input values for either, bus, train,
    * or backup ride in that order calling upon methods from the Bus_Train_Input class.
    * It will then call upon the logic class to determine if the inputs are eligible to being
    * recommended, and finally calls upon the recommendation algorithm to decide which vehicle is
    * the best to use for the user based on their weighted score */
    public static void main(String[] args) {
        //Scanner Object
        Scanner userInput = new Scanner(System.in);
        
        System.out.println("Hello and welcome!");
        
        //String busOrTrain = userInput.nextLine();
        
        //Creates the input object
        Bus_Train_Input input = new Bus_Train_Input();
        
        /*This Variable and while loop is determined by user input, if thee's an inputmismatch it will loop the
        try catch.
         */
        int userOptions = 0;
        
        while(userOptions == 0) {
           /*The try catch will catch incorrect inputs from any of the endloop String inputs
           The While loop within each will loop as many times as needed if the user says "yes" and will
           stop the while loop if the user types in "no"
           
            */
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
                
            }//Will Catch if someone types in a string that isn't yes or no or doesn't type a string at all.
            catch (InputMismatchException e){
                
                System.out.println("\nPlease type yes or no!");
                userInput.nextLine();
                
            } catch (InvalidInputException e) {
                
                System.out.println("\n" + e.getMessage());
                
            }
        }
        
        //Ceates logic object that uses input as an argument
        Bus_Train_Logic logic = new Bus_Train_Logic(input);
        
        //Evaluate the bus and train propositions to see if they ae eligible for comparison
        logic.evaluateLogic();
        
        //Sets up object for the Recommendation_Algorithm that uses input and logic as arguments then
        //use it to call the determine_priority() Method.
        Recommendation_Algorithm recommendation = new Recommendation_Algorithm(input, logic);
        
        recommendation.Determine_Priority();
        
    }
    
    //This determines whether the user inputs "Yes" or "no" for the while loops inside the try and catch block
    public static String yes_Or_No(Scanner userInput) throws InvalidInputException {
        String answer = userInput.nextLine();
        
        if (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            throw new InvalidInputException("\nPlease type yes or no!");
        }
        
        return answer;
    }
    
}