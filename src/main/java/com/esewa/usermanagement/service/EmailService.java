package com.esewa.usermanagement.service;

import com.esewa.usermanagement.entity.User;

public interface EmailService {
    void sendEmail(User userDetail);
}
