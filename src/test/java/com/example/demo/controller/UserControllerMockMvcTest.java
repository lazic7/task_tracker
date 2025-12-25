package com.example.demo.controller;

import com.example.demo.dto.request.CreateUserRequestDTO;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
public class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void getUserById_whenNotFound_shouldReturn404() throws Exception {
        when(userService.loadUserById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_whenOk_shouldReturn201() throws Exception {
        User created = new User();
        created.setId(10L);
        created.setUsername("ana");
        created.setEmail("ana@test.com");

        when(userService.createUser("ana","ana@test.com")).thenReturn(created);

        CreateUserRequestDTO requestDTO = new CreateUserRequestDTO();
        requestDTO.setUsername("ana");
        requestDTO.setEmail("ana@test.com");

        String requestBody = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.username").value("ana"))
                .andExpect(jsonPath("$.email").value("ana@test.com"));
    }

    @Test
    void createUser_whenDuplicate_shouldReturn409() throws Exception {
        when(userService.createUser("ana", "ana@test.com"))
                .thenThrow(new DuplicateResourceException("User with same username or email already exists.", null));

        CreateUserRequestDTO requestDTO = new CreateUserRequestDTO();
        requestDTO.setUsername("ana");
        requestDTO.setEmail("ana@test.com");

        String requestBody = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"));
    }


}
