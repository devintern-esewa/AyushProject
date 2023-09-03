package com.esewa.usermanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomePageController {
    @GetMapping
    public void homePage(){
        System.out.println("In Homepage Controller");
    }
}
