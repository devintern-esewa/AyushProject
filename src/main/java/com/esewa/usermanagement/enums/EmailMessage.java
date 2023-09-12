package com.esewa.usermanagement.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailMessage {
    REGISTRATION(1, "Registration"),
    FORGOT_PASSWORD(2, "Forgot Password");

    private final long templateCode;
    private final String templateMessage;
}
