package com.imposto.service.User;

import com.imposto.dto.LoginDTO;
import com.imposto.dto.UserRequestDTO;
import com.imposto.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUser{

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
        return "";
    }
}
