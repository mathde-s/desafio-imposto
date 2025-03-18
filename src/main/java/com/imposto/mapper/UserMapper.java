package com.imposto.mapper;

import com.imposto.dto.UserResponseDTO;
import com.imposto.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toResponse(UserModel userModel) {
        return new UserResponseDTO(userModel.getId(), userModel.getUsername(), userModel.getRoles());

    }
}
