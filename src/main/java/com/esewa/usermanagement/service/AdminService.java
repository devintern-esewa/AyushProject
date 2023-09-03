package com.esewa.usermanagement.service;

import com.esewa.usermanagement.configuration.ExceptionCodes;
import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.enums.RoleType;
import com.esewa.usermanagement.exceptions.UserAlreadyRegisteredException;
import com.esewa.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
       Optional<User> userExists =  Optional.ofNullable(userRepository.findByName(user.getName()));
       if (userExists.isEmpty()){
//           user.setName(passwordEncoder.encode(user.getName()));
           return userRepository.save(user);
       } else{
           throw new UserAlreadyRegisteredException(ExceptionCodes.UserAlreadyRegisteredCode, ExceptionCodes.UserAlreadyRegisteredMessage);
       }

    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void removeUser(Long userId){
         userRepository.deleteById(userId);
    }

    @Override
    public void run(String... args) throws Exception {
            // Check if the admin user already exists
            if (Optional.ofNullable(userRepository.findByName("aayush")).isEmpty()) {
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

