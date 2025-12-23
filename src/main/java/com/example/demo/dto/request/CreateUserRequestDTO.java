package com.example.demo.dto.request;

public class CreateUserRequestDTO {
    private String username;
    private String email;

    public CreateUserRequestDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
