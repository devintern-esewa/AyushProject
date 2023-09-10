package com.esewa.usermanagement.service;

import com.esewa.usermanagement.configuration.AdminConfigurationProperties;
import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.entity.Wallet;
import com.esewa.usermanagement.exceptions.AlreadyRegisteredException;
import com.esewa.usermanagement.exceptions.UserNotFoundException;
import com.esewa.usermanagement.repository.UserRepository;
import com.esewa.usermanagement.repository.WalletRepository;
import com.esewa.usermanagement.testobjectbuilder.TestUserBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AdminConfigurationProperties adminConfigurationProperties;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testRegisterUserAsync_SuccessfulRegistration() {

        User userToRegister = TestUserBuilder.createUser();

        when(userRepository.findByName(userToRegister.getName())).thenReturn(Optional.empty());
        when(userRepository.save(userToRegister)).then(returnsFirstArg());
        when(walletRepository.save(any(Wallet.class))).then(returnsFirstArg());

        CompletableFuture<User> registrationResult = userService.registerUserAsync(userToRegister);
        User actualRegisteredUser = registrationResult.join();

        assertTrue(registrationResult.isDone());
        assertFalse(registrationResult.isCompletedExceptionally());
        assertEquals(userToRegister.getName(), actualRegisteredUser.getName());

    }

    @Test
    void testRegisterUserAsync_duplicateUsers() {

        User duplicateUser = TestUserBuilder.createUser();

        when(userRepository.findByName(duplicateUser.getName())).thenReturn(Optional.of(duplicateUser));

        assertThrows(RuntimeException.class, () -> userService.registerUserAsync(duplicateUser));
    }

    @Test
    void testRegisterUserAsync_blacklistedUser() {

        User blackListedUser = TestUserBuilder.createBlackListedUser();

        when(userRepository.findByName(blackListedUser.getName()))
                .thenReturn(Optional.empty());
        when(userRepository.save(blackListedUser)).then(returnsFirstArg());

        assertThrows(RuntimeException.class, () -> userService.registerUserAsync(blackListedUser));
    }

    @Test
    void testRegisterUser_successfulRegistration() {

        User user = TestUserBuilder.createUser();

        when(userRepository.findByName(user.getName())).thenReturn(Optional.empty());
        when(userRepository.save(user)).then(returnsFirstArg());
        when(walletRepository.save(any(Wallet.class))).then(returnsFirstArg());

        User registeredUser = userService.registerUser(user);

        assertEquals(user.getName(),registeredUser.getName());
    }

    @Test
    void testRegisterUser_alreadyRegistered() {

        User user = TestUserBuilder.createUser();

        when(userRepository.findByName(user.getName())).thenReturn(Optional.of(user));

        assertThrows(AlreadyRegisteredException.class, () -> userService.registerUser(user));
        verify(userRepository,never()).save(any(User.class));
    }

    @Test
    void testGetUsers_succeeds() {

        List<User> expectedUsers = TestUserBuilder.createUsers();

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getUsers();

        assertEquals(expectedUsers.size(), actualUsers.size());
        for (int i = 0; i < expectedUsers.size(); i++) {
            assertEquals(expectedUsers.get(i), actualUsers.get(i));
        }

    }

    @Test
    void testRemoveUser_succeeds() {

        Long userId = 12L;

        doNothing().when(userRepository).deleteById(userId);

        userService.removeUser(userId);

        verify(userRepository,times(1)).deleteById(userId);
    }

    @Test
    void testGetDetails_validUserId() {

        User user = TestUserBuilder.createUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User actualUserDetails = userService.getDetails(user.getId());

        assertNotNull(actualUserDetails);
        assertEquals(user,actualUserDetails);
    }

    @Test
    void testGetDetails_invalidUserId() {

        Long userId = 24L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getDetails(userId));
    }

    @Test
    void testAdminUser_adminUserDoesNotExist() {

        User user = TestUserBuilder.createAdminUser();

        when(adminConfigurationProperties.getName()).thenReturn(user.getName());
        when(adminConfigurationProperties.getPassword()).thenReturn(user.getPassword());
        when(adminConfigurationProperties.getEmail()).thenReturn(user.getEmail());
        when(adminConfigurationProperties.getRole()).thenReturn(String.valueOf(user.getRole()));
        when(adminConfigurationProperties.getPhone()).thenReturn(user.getPhone());
        when(userRepository.findByName(user.getName())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).then(returnsFirstArg());

        userService.createAdminUser();

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateAdminUser_adminUserExists() {

        User adminUser = TestUserBuilder.createAdminUser();

        when(adminConfigurationProperties.getName()).thenReturn(adminUser.getName());
        when(userRepository.findByName(adminUser.getName())).thenReturn(Optional.of(adminUser));

        userService.createAdminUser();

        verify(userRepository, never()).save(any(User.class));
    }
}
