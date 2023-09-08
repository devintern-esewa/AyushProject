//package com.esewa.usermanagement.service;
//
//import com.esewa.usermanagement.annotation.HashPassword;
//import com.esewa.usermanagement.configuration.AdminConfigurationProperties;
//import com.esewa.usermanagement.constants.Exceptions;
//import com.esewa.usermanagement.entity.User;
//import com.esewa.usermanagement.entity.Wallet;
//import com.esewa.usermanagement.enums.RoleType;
//import com.esewa.usermanagement.exceptions.AlreadyRegisteredException;
//import com.esewa.usermanagement.exceptions.UserNotFoundException;
//import com.esewa.usermanagement.repository.UserRepository;
//import com.esewa.usermanagement.repository.WalletRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.CompletableFuture;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository userRepository;
//    private final WalletRepository walletRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final AdminConfigurationProperties adminConfigurationProperties;
//
//
//    @Override
//    @HashPassword
//    @Transactional
//    @Async("saveMultipleUsersThreadPoolTaskExecutor")
//    public  CompletableFuture<User> registerUserAsync(User user) {
//        Optional<User> userExists = userRepository.findByName(user.getName());
//        if (userExists.isPresent()) {
//            log.error(Exceptions.USER_ALREADY_REGISTERED.getMessage() + new Date());
//            throw new RuntimeException("Duplicate User: "+ user.getName());
//        }
//
//        User savedUser = userRepository.save(user);
//        walletRepository.save(new Wallet(0, savedUser.getId()));
//
//        //Verify rollback on failure
//        if ("shivam".equals(user.getName())) {
//            throw new RuntimeException("Blacklisted User: " + user.getName());
//        }
//
//        return CompletableFuture.completedFuture(savedUser);
//    }
//
//    @Override
//    @Transactional
//    public User registerUser(User user) {
//        Optional<User> userExists = userRepository.findByName(user.getName());
//        if (userExists.isPresent()) {
//            log.error(Exceptions.USER_ALREADY_REGISTERED.getMessage() + new Date());
//            throw new AlreadyRegisteredException();
//        }
//        User savedUser = userRepository.save(user);
//        walletRepository.save(new Wallet(0, savedUser.getId()));
//        return savedUser;
//    }
//
//    @Override
//    public List<User> getUsers(){
//        return userRepository.findAll();
//    }
//
//    @Override
//    public void removeUser(Long userId){
//         userRepository.deleteById(userId);
//    }
//
//    @Override
//    public User getDetails(Long userId) {
//        return userRepository.findById(userId).orElseThrow(() -> {
//            log.error("Invalid Id");
//            return new UserNotFoundException();
//        });
//    }
//
//    @Override
//    public void createAdminUser() {
//        if (userRepository.findByName(adminConfigurationProperties.getName()).isEmpty()) {
//            User adminUser = new User();
//            adminUser.setName(adminConfigurationProperties.getName());
//            adminUser.setPassword(passwordEncoder.encode(adminConfigurationProperties.getPassword()));
//            adminUser.setEmail(adminConfigurationProperties.getEmail());
//            adminUser.setRole(RoleType.valueOf(adminConfigurationProperties.getRole()));
//            adminUser.setPhone(adminConfigurationProperties.getPhone());
//            userRepository.save(adminUser);
//        } else {
//            log.info("Admin user already Exists");
//        }
//    }
//}
//
