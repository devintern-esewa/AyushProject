package com.esewa.usermanagement.exceptions;

import com.esewa.usermanagement.enums.Exceptions;
import com.esewa.usermanagement.dto.ExceptionDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyRegisteredException.class)
    public ExceptionDto alreadyRegisteredException(AlreadyRegisteredException exception) {
        return new ExceptionDto(Exceptions.USER_ALREADY_REGISTERED.getCode(), Exceptions.USER_ALREADY_REGISTERED.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ExceptionDto alreadyRegisteredException(UserNotFoundException exception) {
        return new ExceptionDto(Exceptions.USER_NOT_FOUND.getCode(), Exceptions.USER_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(UserRegistrationFailedException.class)
    public ExceptionDto userRegistrationFailedException(UserNotFoundException exception) {
        return new ExceptionDto(Exceptions.USER_REGISTRATION_FAILED.getCode(), Exceptions.USER_REGISTRATION_FAILED.getMessage());
    }

}
