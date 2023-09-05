package com.esewa.usermanagement.exceptions;

import com.esewa.usermanagement.constants.ExceptionMessages;
import com.esewa.usermanagement.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyRegisteredException.class)
    public ExceptionDto alreadyRegisteredException(AlreadyRegisteredException exception){
        return new ExceptionDto(ExceptionMessages.USER_ALREADY_REGISTERED_CODE, exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ExceptionDto alreadyRegisteredException(UserNotFoundException exception){
        return new ExceptionDto(ExceptionMessages.USER_NOT_FOUND_CODE, exception.getMessage());
    }

    @ExceptionHandler(UserRegistrationFailedException.class)
    public ExceptionDto userRegistrationFailedException(UserNotFoundException exception){
        return new ExceptionDto(ExceptionMessages.USER_REGISTRATION_FAILED_CODE, exception.getMessage());
    }

}
