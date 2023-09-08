//package com.esewa.usermanagement.aspect;
//
//import com.esewa.usermanagement.entity.User;
//import lombok.RequiredArgsConstructor;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//@Aspect
//@RequiredArgsConstructor
//public class PasswordHashingAspect {
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Before("@annotation(com.esewa.usermanagement.annotation.HashPassword) && args(user)")
//    public void hashPassword(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//    }
//}
