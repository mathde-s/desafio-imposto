package com.imposto.service.User;

import com.imposto.dto.LoginDTO;
import com.imposto.dto.UserRequestDTO;
import com.imposto.dto.UserResponseDTO;

public interface IUser {
    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);
    String login(LoginDTO loginDTO);
}
