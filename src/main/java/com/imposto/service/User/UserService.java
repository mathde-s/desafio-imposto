package com.imposto.service.User;

import com.imposto.dto.LoginDTO;
import com.imposto.dto.UserRequestDTO;
import com.imposto.dto.UserResponseDTO;
import com.imposto.exceptions.ExistingResourceException;
import com.imposto.mapper.UserMapper;
import com.imposto.model.RoleModel;
import com.imposto.model.UserModel;
import com.imposto.repository.RoleRepository;
import com.imposto.repository.UserRepository;
import com.imposto.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUser{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO){
        if (userRepository.existsByUsername(userRequestDTO.getUsername()))
            throw new ExistingResourceException("username ja cadastrado");

        UserModel user = new UserModel();
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        Set<RoleModel> roles = userRequestDTO.getRoles().stream()
                .map(r -> new RoleModel(r.name())).collect(Collectors.toSet());
        roleRepository.saveAll(roles);

        user.setRoles(roles);

        userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }
}
