package com.imposto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaxRequestDTO {
    @NotBlank(message = "o nome não pode estar vazio")
    private String name;
    @NotBlank(message = "a descrição não pode ser vazia")
    @Size(max = 500)
    private String description;

    @Positive(message = "a aliquota deve ser positiva")
    private double aliquota;

    public TaxRequestDTO(String name){
        this.name = name;
    }
}
