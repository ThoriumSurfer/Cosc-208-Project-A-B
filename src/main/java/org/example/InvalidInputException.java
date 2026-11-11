package org.example;


//Throws a custom exception for incorrect user inputs
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}