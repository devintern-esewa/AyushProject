package com.esewa.usermanagement.service;

import com.esewa.usermanagement.constants.ExceptionMessages;
import com.esewa.usermanagement.dto.JwtResponse;
import com.esewa.usermanagement.dto.LoginDto;
import com.esewa.usermanagement.exceptions.UserNotFoundException;
import com.esewa.usermanagement.jwt.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    public ResponseEntity<JwtResponse> loginUser(LoginDto credentials) {

       // try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword()));
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String token = this.jwtHelper.generateToken(authentication.getName());
                JwtResponse response = JwtResponse.builder()
                        .jwtToken(token)
                        .username(authentication.getName()).build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
       // } catch (Exception ex) {

            throw new UserNotFoundException(ExceptionMessages.UserNotFoundMessage);
     //   }
    }
}
