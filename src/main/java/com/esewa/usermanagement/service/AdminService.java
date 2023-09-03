package com.esewa.usermanagement.service;

import com.esewa.usermanagement.annotation.HashPassword;
import com.esewa.usermanagement.constants.ExceptionMessages;
import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.exceptions.AlreadyRegisteredException;
import com.esewa.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @HashPassword
    public User registerUser(User user) {
        System.out.println(user.getPassword());
        Optional<User> userExists = userRepository.findByName(user.getName());
        if (userExists.isEmpty()) {
            return userRepository.save(user);
        }
        throw new AlreadyRegisteredException(ExceptionMessages.UserAlreadyRegisteredMessage);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void removeUser(Long userId){
         userRepository.deleteById(userId);
    }

}

