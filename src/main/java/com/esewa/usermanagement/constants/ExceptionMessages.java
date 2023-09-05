package com.esewa.usermanagement.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {
    public static final String USER_ALREADY_REGISTERED_CODE = "1000";
    public static final String DUPLICATE_USER = "Duplicate User";
    public static final String USER_NOT_FOUND_CODE = "1001";
    public static final String USER_NOT_FOUND_MESSAGE = "User Not Found";
    public static final String USER_REGISTRATION_FAILED_CODE = "1002";
    public static final String USER_REGISTRATION_FAILED_MESSAGE = "User Registration Failed";
}
