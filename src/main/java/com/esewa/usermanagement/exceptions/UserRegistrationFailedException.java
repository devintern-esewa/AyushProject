package com.esewa.usermanagement.exceptions;

public class UserRegistrationFailedException extends RuntimeException{
    public UserRegistrationFailedException(String message){
        super(message);
    }
}
