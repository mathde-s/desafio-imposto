package com.imposto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaxResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double aliquota;

    public TaxResponseDTO(Long id, String name, String description, Double aliquota) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.aliquota = aliquota;
    }

    public TaxResponseDTO(String name){
        this.name = name;
    }
}
