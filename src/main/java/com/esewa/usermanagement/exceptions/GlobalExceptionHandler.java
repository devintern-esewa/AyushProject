package com.esewa.usermanagement.exceptions;

import com.esewa.usermanagement.dto.ExceptionDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ExceptionDto userAlreadyRegistered(UserAlreadyRegisteredException exception){
            return new ExceptionDto(exception.getCode(), exception.getMessage());
    }
}
