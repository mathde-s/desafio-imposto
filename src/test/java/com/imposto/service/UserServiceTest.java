package com.imposto.service;

import com.imposto.dto.LoginDTO;
import com.imposto.dto.Roles;
import com.imposto.dto.UserRequestDTO;
import com.imposto.dto.UserResponseDTO;
import com.imposto.exceptions.ExistingResourceException;
import com.imposto.mapper.UserMapper;
import com.imposto.model.RoleModel;
import com.imposto.model.UserModel;
import com.imposto.repository.RoleRepository;
import com.imposto.repository.UserRepository;
import com.imposto.infra.security.jwt.JwtTokenProvider;
import com.imposto.service.User.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

import static com.imposto.dto.Roles.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUser(){
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setUsername("testUser");
        requestDTO.setPassword("password123");
        requestDTO.setRoles(Set.of(ROLE_USER));

        UserModel userModel = new UserModel();
        userModel.setUsername("testUser");
        userModel.setPassword("encodedPassword");

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername("testUser");

        when(userRepository.existsByUsername("testUser")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userMapper.toResponse(any(UserModel.class))).thenReturn(userResponseDTO);


        UserResponseDTO response = userService.registerUser(requestDTO);

        verify(userRepository).existsByUsername("testUser");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(UserModel.class));
        verify(roleRepository).saveAll(anySet());
        assertEquals("testUser", response.getUsername());
    }

    @Test
    void shouldFailToRegisterUserWithExistingUsername(){
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setUsername("test");
        requestDTO.setPassword("password123");
        requestDTO.setRoles(Set.of(ROLE_USER));

        when(userRepository.existsByUsername(requestDTO.getUsername())).thenReturn(true);

        ExistingResourceException ex = assertThrows(ExistingResourceException.class, () -> {
            userService.registerUser(requestDTO);
        });

        assertEquals("username ja cadastrado", ex.getMessage());
        verify(userRepository).existsByUsername("test");
    }

    @Test
    void shouldLogin(){
        LoginDTO dto = new LoginDTO();
        dto.setUsername("user");
        dto.setPassword("12345");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn("mockedToken");

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        String token = userService.login(dto);

        assertEquals("mockedToken", token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).generateToken(authentication);
    }

    @Test
    void shouldFailToLogin(){
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("test");
        loginDTO.setPassword("wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> userService.login(loginDTO)
        );

        assertEquals("Invalid credentials", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }


}
