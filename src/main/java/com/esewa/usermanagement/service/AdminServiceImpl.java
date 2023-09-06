package com.esewa.usermanagement.service;

import com.esewa.usermanagement.entity.RegistrationLog;
import com.esewa.usermanagement.entity.User;
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
    private final RegistrationLogService logService;

    public AdminServiceImpl (UserService userService, RegistrationLogService logService) {
        this.userService = userService;
        this.logService = logService;
        this.createAdminUser();
    }

    @Override
    public User registerUser(User user) {

        return userService.registerUser(user);

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
                    logService.saveLog(new RegistrationLog(ex.getMessage(), new Date()));
                } else {
                    logService.saveLog(new RegistrationLog("Registered Successfully: " + user.getName(), new Date()));
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
