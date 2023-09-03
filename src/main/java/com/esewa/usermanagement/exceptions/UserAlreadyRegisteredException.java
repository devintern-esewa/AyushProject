package com.esewa.usermanagement.exceptions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserAlreadyRegisteredException extends RuntimeException{
    private String code;
    private String message;
}
