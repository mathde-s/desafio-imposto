package com.imposto.mapper;

import com.imposto.dto.UserResponseDTO;
import com.imposto.model.UserModel;

public class UserMapper {
    public static UserResponseDTO toResponse(UserModel userModel) {
        return new UserResponseDTO(userModel.getId(), userModel.getUsername(), userModel.getRoles());

    }
}
