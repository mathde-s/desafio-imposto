package com.imposto.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "taxes")
public class TaxModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Double aliquota;

    public TaxModel(String name, String description, double aliquota) {
        this.name = name;
        this.description = description;
        this.aliquota = aliquota;
    }
}
