package com.esewa.usermanagement.exceptions;

import com.esewa.usermanagement.dto.ExceptionDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyRegisteredException.class)
    public ExceptionDto AlreadyRegisteredException(AlreadyRegisteredException exception){
        return new ExceptionDto("504", exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ExceptionDto AlreadyRegisteredException(UserNotFoundException exception){
        return new ExceptionDto("505", exception.getMessage());
    }

}
