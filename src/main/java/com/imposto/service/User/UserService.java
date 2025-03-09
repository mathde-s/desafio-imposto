package com.imposto.service.User;

import com.imposto.dto.LoginDTO;
import com.imposto.dto.UserRequestDTO;
import com.imposto.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUser{

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO){
        return new UserResponseDTO();
    }

    public String login(LoginDTO loginDTO) {
        return "";
    }
}
