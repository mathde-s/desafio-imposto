package com.imposto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TaxModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double aliquota;

    public TaxModel(String name, String description, double aliquota) {
        this.name = name;
        this.description = description;
        this.aliquota = aliquota;
    }
}
