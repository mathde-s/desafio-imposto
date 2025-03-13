package com.imposto.controller;

import com.imposto.dto.AuthResponseDTO;
import com.imposto.dto.LoginDTO;
import com.imposto.dto.UserRequestDTO;
import com.imposto.dto.UserResponseDTO;
import com.imposto.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO){
        try{
            UserResponseDTO response = userService.registerUser(userRequestDTO);
            log.info("user created successfully with username: {}", response.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error creating user with username:{} ", userRequestDTO.getUsername(), e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        String token = userService.login(loginDTO);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setAccessToken(token);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }
}
