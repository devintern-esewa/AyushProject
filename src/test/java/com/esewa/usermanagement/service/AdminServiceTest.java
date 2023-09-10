package com.esewa.usermanagement.service;

import com.esewa.usermanagement.entity.RegistrationLog;
import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.testobjectbuilder.TestUserBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @Mock
    private RegistrationLogService registrationLogService;
    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    void givenValidUser_whenRegisterUser_thenSucceed() {

        User user = TestUserBuilder.createUser();

        when(userService.registerUser(user)).then(returnsFirstArg());
        doNothing().when(emailService).sendEmail(user);
        User actualResult = adminService.registerUser(user);

        assertEquals(user, actualResult);
    }

    @Test
    void givenValidUsers_whenRegisterMultipleUsers_thenSucceed() {

        List<User> usersToRegister = TestUserBuilder.createUsers();

        doNothing().when(emailService).sendEmail(any(User.class));
        doNothing().when(registrationLogService).saveLog(any(RegistrationLog.class));
        when(userService.registerUserAsync(any(User.class)))
                .thenAnswer(invocation -> CompletableFuture.completedFuture(invocation.getArgument(0)));

        List<User> registeredUsers = adminService.registerMultipleUsers(usersToRegister);

        assertEquals(usersToRegister.size(), registeredUsers.size());
        verify(userService, times(usersToRegister.size())).registerUserAsync(any(User.class));

    }

    @Test
    void testRegisterMultipleUsers_throwsException_forBlacklistedUser() {

        List<User> users = TestUserBuilder.createUsers();

        doNothing().when(emailService).sendEmail(any(User.class));
        doNothing().when(registrationLogService).saveLog(any(RegistrationLog.class));
        when(userService.registerUserAsync(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    return !"shivam".equals(user.getName())
                            ? CompletableFuture.completedFuture(user)
                            : CompletableFuture.failedFuture(new RuntimeException("Blacklisted User"));
                });

        List<User> registeredUsers = adminService.registerMultipleUsers(users);

        assertEquals(users.size()-1,registeredUsers.size());
    }

    @Test
    void testGetUsers_succeeds() {

        List<User> users = TestUserBuilder.createUsers();

        when(userService.getUsers()).thenReturn(users);

        List<User> actualUsers = adminService.getUsers();

        assertEquals(users.size(), actualUsers.size());
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i), actualUsers.get(i));
        }
    }


    @Test
    void testRemoveUser_succeeds() {

        Long userIdToRemove = 133L;

        doNothing().when(userService).removeUser(userIdToRemove);

        adminService.removeUser(userIdToRemove);

        verify(userService, times(1)).removeUser(userIdToRemove);
    }

}
