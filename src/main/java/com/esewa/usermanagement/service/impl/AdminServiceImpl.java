package com.esewa.usermanagement.service.impl;

import com.esewa.usermanagement.entity.RegistrationLog;
import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.notification.service.EmailService;
import com.esewa.usermanagement.service.AdminService;
import com.esewa.usermanagement.service.RegistrationLogService;
import com.esewa.usermanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final RegistrationLogService registrationLogService;
    private final EmailService emailService;

    public AdminServiceImpl (UserService userService, RegistrationLogService registrationLogService, EmailService emailService) {
        this.userService = userService;
        this.registrationLogService = registrationLogService;
        this.emailService = emailService;
        this.createAdminUser();
    }

    @Override
    public User registerUser(User user) {

        User registeredUser = userService.registerUser(user);
        emailService.sendNotification(registeredUser);
        return registeredUser;

    }

    @Override
    public List<User> registerMultipleUsers(List<User> users) {

        List<CompletableFuture<User>> asyncUserRegistrationList = users.stream()
                .map(userService::registerUserAsync)
                .toList();

        logRegistration(asyncUserRegistrationList);

        try {
            CompletableFuture<Void> allOf = CompletableFuture.allOf(asyncUserRegistrationList.toArray(new CompletableFuture[0]));
            allOf.join();
        } catch (Exception e) {
            log.error("Error in one or more registration tasks: " + e.getMessage());
        }

        return getSuccessfullyRegisteredUsers(asyncUserRegistrationList);
    }

    private void logRegistration(List<CompletableFuture<User>> asyncUserRegistrationList) {
        for (CompletableFuture<User> future : asyncUserRegistrationList) {
            future.handle((user, ex) -> {
                if (ex != null) {
                    log.error("Error creating user: " + ex.getMessage());
                    registrationLogService.saveLog(new RegistrationLog(ex.getMessage(), new Date()));
                } else {
                    registrationLogService.saveLog(new RegistrationLog("Registered Successfully: " + user.getName(), new Date()));
                    emailService.sendNotification(user);
                }
                return user;
            });
        }
    }

    private List<User> getSuccessfullyRegisteredUsers(List<CompletableFuture<User>> asyncUserRegistrationList) {

        List<User> registeredUserList = new ArrayList<>();
        for (CompletableFuture<User> future : asyncUserRegistrationList) {
            try {
                User user = future.get();
                if (user != null) {
                    registeredUserList.add(user);
                }
            } catch (Exception e) {
                log.error("Registration Failed: " + e.getMessage());
            }
        }
        return registeredUserList;

    }

    @Override
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @Override
    public void removeUser(Long userId) {
        userService.removeUser(userId);
    }

    public void createAdminUser() {
        this.userService.createAdminUser();
    }

}
