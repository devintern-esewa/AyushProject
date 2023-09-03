package com.esewa.usermanagement.service;

import com.esewa.usermanagement.constants.ExceptionMessages;
import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.exceptions.UserNotFoundException;
import com.esewa.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByName(username);
        if (user.isEmpty())
            throw  new UserNotFoundException(ExceptionMessages.UserNotFoundMessage);
        return new CustomUserDetail(user.orElse(null));
    }
}
