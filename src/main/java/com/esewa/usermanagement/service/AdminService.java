package com.esewa.usermanagement.service;

import com.esewa.usermanagement.entity.User;

import java.util.List;

public interface AdminService {
    User registerUser(User user);

    List<User> registerMultipleUsers(List<User> users);

    List<User> getUsers();

    void removeUser(Long userId);
}
