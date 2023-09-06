package com.esewa.usermanagement.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Exceptions {

    USER_ALREADY_REGISTERED("1000", "Duplicate user"),
    USER_NOT_FOUND("1001", "User not found"),
    USER_REGISTRATION_FAILED("1002", "User registration failed");

    private final String code;
    private final String message;

    }
