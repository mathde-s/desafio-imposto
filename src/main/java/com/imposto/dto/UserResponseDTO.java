package com.imposto.dto;

import com.imposto.model.RoleModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
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
