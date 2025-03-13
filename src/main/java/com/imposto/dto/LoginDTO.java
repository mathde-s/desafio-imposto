package com.imposto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank(message = "o username não pode ser vazia")
    private String username;

    @NotBlank(message = "a senha não pode ser vazia")
    private String password;
}
