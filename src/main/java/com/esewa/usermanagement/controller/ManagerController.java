package com.esewa.usermanagement.controller;

import com.esewa.usermanagement.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @GetMapping("/user")
    public void users(){
        System.out.println("In Manager Controller");
    }
}
