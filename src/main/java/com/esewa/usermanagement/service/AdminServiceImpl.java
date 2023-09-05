package com.esewa.usermanagement.service;

import com.esewa.usermanagement.constants.ExceptionMessages;
import com.esewa.usermanagement.entity.RegistrationLog;
import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.enums.RoleType;
import com.esewa.usermanagement.exceptions.UserRegistrationFailedException;
import com.esewa.usermanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService{

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LogService logService;

    public AdminServiceImpl (UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, LogService logService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.logService = logService;
        this.createAdminUser();
    }

    @Override
    public User registerUser(User user) {
        try {
            CompletableFuture<User> userFuture = userService.registerUser(user);
            return userFuture.get();
        } catch (Exception exception) {
            log.info(exception.getMessage());
            throw new UserRegistrationFailedException(ExceptionMessages.USER_REGISTRATION_FAILED_MESSAGE);
        }
    }

    @Override
    public List<User> registerMultipleUsers(List<User> userList) {

        List<CompletableFuture<User>> asyncUserRegistrationList = new ArrayList<>();
        List<User> registeredUserList = new ArrayList<>();
        for (User user : userList) {
            CompletableFuture<User> future = userService.registerUser(user);
            asyncUserRegistrationList.add(future);
        }
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

        try {
            CompletableFuture<Void> allOf = CompletableFuture.allOf(asyncUserRegistrationList.toArray(new CompletableFuture[0]));
            allOf.join();
        } catch (Exception e) {
            log.error("Error in one or more registration tasks: " + e.getMessage());
        }

        //Getting List of successfully registered users
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
        if (userRepository.findByName("aayush").isEmpty()) {
            User adminUser = new User();
            adminUser.setName("Aayush");
            adminUser.setPassword(passwordEncoder.encode("aayush"));
            adminUser.setEmail("caayush96@gmail.com");
            adminUser.setRole(RoleType.ROLE_ADMIN);
            adminUser.setPhone("9844430402");
            userRepository.save(adminUser);
        } else {
            log.info("Admin user already Exists");
        }
    }
}
