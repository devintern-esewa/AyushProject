package com.esewa.usermanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/manager")
public class ManagerController {
    @GetMapping("/user")
    public void users(){
        System.out.println("In Manager Controller");
    }
}
