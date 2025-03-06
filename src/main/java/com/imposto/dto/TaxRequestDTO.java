package com.imposto.dto;

import lombok.Data;

@Data
public class TaxRequestDTO {
    private String name;
    private String description;
    private double aliquota;

}
