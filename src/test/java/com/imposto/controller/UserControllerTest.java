package com.imposto.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.imposto.dto.AuthResponseDTO;
import com.imposto.dto.LoginDTO;
import com.imposto.dto.UserRequestDTO;
import com.imposto.dto.UserResponseDTO;
import com.imposto.service.User.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterUser() {
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setUsername("test");
        requestDTO.setPassword("password123");

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUsername("test");
        responseDTO.setId(1L);

        when(userService.registerUser(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<UserResponseDTO> response = userController.registerUser(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test", response.getBody().getUsername());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void shouldFailToRegisterUser() {
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setUsername("test");
        requestDTO.setPassword("password123");

        when(userService.registerUser(requestDTO)).thenThrow(new RuntimeException("Registration error"));

        ResponseEntity<UserResponseDTO> response = userController.registerUser(requestDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldLogin() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testUser");
        loginDTO.setPassword("password123");

        String token = "mocked-jwt-token";
        when(userService.login(loginDTO)).thenReturn(token);

        ResponseEntity<AuthResponseDTO> response = userController.login(loginDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(token, response.getBody().getAccessToken());
    }

    @Test
    void shouldFailToLogin() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testUser");
        loginDTO.setPassword("password123");

        when(userService.login(loginDTO)).thenThrow(new RuntimeException("Login error"));

        ResponseEntity<AuthResponseDTO> response = userController.login(loginDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
