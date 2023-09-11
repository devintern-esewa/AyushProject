package com.esewa.usermanagement.service;

import com.esewa.usermanagement.dto.JwtResponse;
import com.esewa.usermanagement.dto.LoginDto;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<JwtResponse> loginUser(LoginDto credentials);
}
