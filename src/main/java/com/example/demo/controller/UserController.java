package com.example.demo.controller;

import com.example.demo.dto.request.CreateUserRequestDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.loadAllUsers()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.loadUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequestDTO req) {
        User created = userService.createUser(req.getUsername(), req.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


}
