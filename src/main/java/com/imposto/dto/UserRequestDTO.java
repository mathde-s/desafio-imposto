package com.imposto.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserRequestDTO {
    private String username;
    private String password;
    private Set<Roles> roles;
}
