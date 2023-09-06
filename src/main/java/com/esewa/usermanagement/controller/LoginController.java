package com.esewa.usermanagement.controller;

import com.esewa.usermanagement.dto.JwtResponse;
import com.esewa.usermanagement.dto.LoginDto;
import com.esewa.usermanagement.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/homepage")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public void homePage(){
        log.info("In Homepage");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDto credentials){
        return loginService.loginUser(credentials);
    }

}
