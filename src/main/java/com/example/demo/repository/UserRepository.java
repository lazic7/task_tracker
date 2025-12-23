package com.example.demo.repository;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAllUsers();
    Optional<User> findUserById(Long id);
    User createUser(String username, String email);
}
