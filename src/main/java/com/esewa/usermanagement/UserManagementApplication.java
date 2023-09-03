package com.esewa.usermanagement;

import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.enums.RoleType;
import com.esewa.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class UserManagementApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder  passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Checks the existence of admin user
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
