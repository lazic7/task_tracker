package com.example.demo.service;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void createUser_whenUsernameBlank_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("", "test@test.com");
        });
    }

    @Test
    void createUser_whenEmailInvalid_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("ana", "not-email");
        });
    }

    @Test
    void createUser_whenRepositoryThrowsDataIntegrityViolation_shouldThrowDuplicateResourceException() {
        when(userRepository.createUser("ana", "ana@test.com"))
                .thenThrow(new DataIntegrityViolationException("unique constraint"));

        assertThrows(DuplicateResourceException.class, () -> {
            userService.createUser("ana", "ana@test.com");
        });
    }


}
