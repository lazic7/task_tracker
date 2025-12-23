package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> loadAllUsers();
    Optional<User> loadUserById(Long id);
    User createUser(String username, String email);
}
