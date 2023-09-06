package com.esewa.usermanagement.controller;

import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/details/{id}")
    public User details(@PathVariable("id") Long userId){
        return userService.getDetails(userId);
    }

}
