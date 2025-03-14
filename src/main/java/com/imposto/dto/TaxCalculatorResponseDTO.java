package com.imposto.dto;

import lombok.Data;

@Data
public class TaxCalculatorResponseDTO {
    private String TaxType;
    private Double baseValue;
    private Double aliquota;
    private Double taxValue;

    public TaxCalculatorResponseDTO(String taxType, Double baseValue, Double aliquota, Double taxValue) {
        this.TaxType = taxType;
        this.baseValue = baseValue;
        this.aliquota = aliquota;
        this.taxValue = taxValue;
    }
}
