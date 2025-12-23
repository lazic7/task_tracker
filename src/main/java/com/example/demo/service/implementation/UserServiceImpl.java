package com.example.demo.service.implementation;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");


    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> loadAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public Optional<User> loadUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User createUser(String username, String email) {
        if (!StringUtils.hasText(username) || username.length() > 50) {
            throw new IllegalArgumentException("Username is required and must be <= 50 chars.");
        }
        if (!StringUtils.hasText(email) || email.length() > 120 || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email is required and must be a valid email address.");
        }

        try {
            return userRepository.createUser(username.trim(), email.trim().toLowerCase());
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateResourceException("User with same username or email already exists.", ex);
        }
    }


}
