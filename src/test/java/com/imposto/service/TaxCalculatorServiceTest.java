package com.imposto.service;

import com.imposto.dto.TaxCalculatorRequestDTO;
import com.imposto.dto.TaxCalculatorResponseDTO;
import com.imposto.exceptions.ResourceNotFoundException;
import com.imposto.model.TaxModel;
import com.imposto.repository.TaxRepository;
import com.imposto.service.TaxCalculator.TaxCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaxCalculatorServiceTest {

    @Mock
    private TaxRepository taxRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    private TaxCalculatorService taxCalculatorService;

    @Test
    void shouldCalculateTax() {
        Long taxId = 1L;
        double baseValue = 1000.0;
        double aliquota = 10.0;
        TaxModel tax = new TaxModel();
        tax.setId(taxId);
        tax.setName("test");
        tax.setAliquota(aliquota);

        TaxCalculatorRequestDTO calculatorDTO = new TaxCalculatorRequestDTO();
        calculatorDTO.setTaxId(taxId);
        calculatorDTO.setBaseValue(baseValue);

        when(taxRepository.findById(taxId)).thenReturn(Optional.of(tax));

        TaxCalculatorResponseDTO result = taxCalculatorService.calculateTax(calculatorDTO);

        verify(taxRepository).findById(taxId);
        assertEquals("test", result.getTaxType());
        assertEquals(baseValue, result.getBaseValue());
        assertEquals(aliquota, result.getAliquota());
        assertEquals(100.0, result.getTaxValue());
    }

    @Test
    void shouldFailWhenTaxNotFound() {
        Long taxId = 1L;
        TaxCalculatorRequestDTO calculatorDTO = new TaxCalculatorRequestDTO();
        calculatorDTO.setTaxId(taxId);
        calculatorDTO.setBaseValue(1000.0);

        when(taxRepository.findById(taxId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> taxCalculatorService.calculateTax(calculatorDTO)
        );

        assertEquals("imposto não encontrado", exception.getMessage());
        verify(taxRepository).findById(taxId);
    }
}
