package com.imposto.dto;

import com.imposto.model.RoleModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String username;
    private String password;
    private RoleModel roles;
}
