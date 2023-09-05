package com.esewa.usermanagement.service;

import com.esewa.usermanagement.entity.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {

   CompletableFuture<User> registerUser(User user);

   List<User> getUsers();

   void removeUser(Long userId);

   User getDetails(Long userId);
}
