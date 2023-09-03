package com.esewa.usermanagement.controller;

import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/user")
    public User register(@RequestBody User user) {
        return adminService.registerUser(user);
    }

    @GetMapping("/user")
    public List<User> users(){
        return adminService.getUsers();
    }

    @PostMapping("/user/{id}")
    public void remove(@PathVariable("id") Long userId){
         adminService.removeUser(userId);
    }

}
