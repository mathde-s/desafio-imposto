package com.imposto.dto;

import com.imposto.model.RoleModel;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private Set<RoleModel> roles;

    public UserResponseDTO(Long id, String username, Set<RoleModel> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
