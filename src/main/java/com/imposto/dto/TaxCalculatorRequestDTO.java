package com.imposto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TaxCalculatorRequestDTO {
    @NotNull(message = "o id não pode ser nulo")
    @Positive(message = "o id deve ser positivo")
    private Long taxId;

    @NotNull(message = "o valor base não pode ser nulo")
    @Positive(message = "o valor base deve ser positivo")
    private Double baseValue;
}
