package com.esewa.usermanagement.service;

import com.esewa.usermanagement.annotation.HashPassword;
import com.esewa.usermanagement.constants.ExceptionMessages;
import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.entity.Wallet;
import com.esewa.usermanagement.exceptions.UserNotFoundException;
import com.esewa.usermanagement.repository.UserRepository;
import com.esewa.usermanagement.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Override
    @HashPassword
    @Transactional
    @Async("saveMultipleUsersThreadPoolTaskExecutor")
    public  CompletableFuture<User> registerUser(User user) {
        System.out.println(Thread.currentThread());
        Optional<User> userExists = userRepository.findByName(user.getName());
        if (userExists.isPresent()) {
            log.error(ExceptionMessages.DUPLICATE_USER + new Date());
            throw new RuntimeException("Duplicate User: "+ user.getName());
        }
        User savedUser = userRepository.save(user);
        walletRepository.save(new Wallet(0, savedUser.getId()));
        if (user.getName().equals("shivam")) {
            throw new RuntimeException("Blacklisted User: " + user.getName());
        }
        return CompletableFuture.completedFuture(savedUser);
    }

    @Override
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @Override
    public void removeUser(Long userId){
         userRepository.deleteById(userId);
    }

    @Override
    public User getDetails(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.info("Invalid Id");
            return new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND_MESSAGE);
        });
    }
}

