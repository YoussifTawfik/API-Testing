package com.learning.testing.exception;

public class EmployeeAlreadyExists extends RuntimeException{

    public EmployeeAlreadyExists(String message){
        super(message);
    }
}
