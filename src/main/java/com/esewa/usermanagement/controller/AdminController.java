package com.esewa.usermanagement.controller;

import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
/*We can specify role even in class and methods
For this we have to add, @EnableGlobalMethodSecurity(prePostEnabled = true) and use @PreAuthorize annotation
@PreAuthorize("hasRole(ADMIN)") :*/
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

   /* @GetMapping("/home")
    public void homepage(){
        System.out.println("Homepage");;
    }*/


    /*   @PostMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable("id") Long userId){
        return adminService.updateUser(user,userId);
    }*/
    @PostMapping("/user/{id}")
    public void remove(@PathVariable("id") Long userId){
         adminService.removeUser(userId);
    }
}
