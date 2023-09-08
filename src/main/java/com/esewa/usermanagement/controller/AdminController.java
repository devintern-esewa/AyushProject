//package com.esewa.usermanagement.controller;
//
//import com.esewa.usermanagement.entity.User;
//import com.esewa.usermanagement.service.AdminService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/admin")
//public class AdminController {
//
//    private final AdminService adminService;
//
//    @PostMapping("/user")
//    public User register(@RequestBody User user) {
//        return adminService.registerUser(user);
//    }
//
//    @PostMapping("/multiple-users")
//    public List<User> registerMultipleUsers(@RequestBody List<User> users) {
//         return adminService.registerMultipleUsers(users);
//    }
//
//    @GetMapping("/user")
//    public List<User> users(){
//        return adminService.getUsers();
//    }
//
//    @PostMapping("/user/{id}/remove")
//    public void remove(@PathVariable("id") Long userId){
//         adminService.removeUser(userId);
//    }
//
//}
