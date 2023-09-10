package com.esewa.usermanagement.testobjectbuilder;

import com.esewa.usermanagement.entity.User;
import com.esewa.usermanagement.enums.RoleType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUserBuilder {

    public static User createUser() {
        return new User(1L, "himal", "himal", "9844870402", "himal@gmail.com", RoleType.ROLE_USER);
    }

    public static User createAdminUser() {
        return new User(1L, "aayush", "aayush", "9844430402", "caayush@gmail.com", RoleType.ROLE_ADMIN);
    }

    public static User createBlackListedUser() {
        return new User(1L, "shivam", "shivam", "9847830402", "shivam@gmail.com", RoleType.ROLE_USER);
    }

    public static List<User> createUsers() {
        User user1 = new User(1L, "aayush", "aayush", "9844430402", "caayush@gmail.com", RoleType.ROLE_USER);
        User user2 = new User(2L, "kundan", "kundan", "9844430842", "kundan@gmail.com", RoleType.ROLE_USER);
        User user3 = new User(3L, "angad", "angad", "9844438972", "angad@gmail.com", RoleType.ROLE_USER);
        User user4 = new User(4L, "shivam", "shivam", "9887438972", "shivam@gmail.com", RoleType.ROLE_USER);
        return Arrays.asList(user1, user2, user3, user4);
    }

}
