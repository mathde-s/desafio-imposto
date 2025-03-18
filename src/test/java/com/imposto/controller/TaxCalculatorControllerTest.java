package com.imposto.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.imposto.dto.TaxCalculatorRequestDTO;
import com.imposto.dto.TaxCalculatorResponseDTO;
import com.imposto.service.TaxCalculator.TaxCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class TaxCalculatorControllerTest {

    @InjectMocks
    private TaxCalculatorController taxCalculatorController;

    @Mock
    private TaxCalculatorService taxCalculatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCalculateTax() {
        TaxCalculatorRequestDTO requestDTO = new TaxCalculatorRequestDTO();
        requestDTO.setTaxId(1L);
        requestDTO.setBaseValue(100.0);

        TaxCalculatorResponseDTO responseDTO = new TaxCalculatorResponseDTO();
        responseDTO.setTaxType("test");
        responseDTO.setBaseValue(100.0);
        responseDTO.setAliquota(20.0);

        when(taxCalculatorService.calculateTax(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<TaxCalculatorResponseDTO> response = taxCalculatorController.calculateTax(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test", response.getBody().getTaxType());
        assertEquals(100.0, response.getBody().getBaseValue());
        assertEquals(20.0, response.getBody().getAliquota());
    }

    @Test
    void shouldFailToCalculateTax() {
        TaxCalculatorRequestDTO requestDTO = new TaxCalculatorRequestDTO();
        requestDTO.setTaxId(1L);
        requestDTO.setBaseValue(100.0);

        when(taxCalculatorService.calculateTax(requestDTO)).thenThrow(new RuntimeException("Calculation error"));

        ResponseEntity<TaxCalculatorResponseDTO> response = taxCalculatorController.calculateTax(requestDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
