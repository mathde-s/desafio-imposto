package com.imposto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserRequestDTO {
    private String username;
    private String password;
    private Set<Roles> roles;
}
