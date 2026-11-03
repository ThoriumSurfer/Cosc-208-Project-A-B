package org.example;
import org.example.InvalidInputException;
//import org.example.InvalidWeatherException;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/*Cosc 208 Project A and B
 Makail Casey*/

public class Main {
    
    public static void main(String[] args) {
        
        Scanner userInput = new Scanner(System.in);
        
        System.out.println("Hello and welcome!");
        
        String busOrTrain = userInput.nextLine();
        // Create the input object
        Bus_Train_Input input = new Bus_Train_Input();
        
        // Collect the user's information
        input.Bus_Inputs();
        input.Train_Inputs();
        
        // Create the logic object and pass it the input
        Bus_Train_Logic logic = new Bus_Train_Logic(input);
        
        // Evaluate the bus and train propositions
        logic.evaluatePropositions();
        
    }
    
}