package com.imposto.controller;

import com.imposto.dto.TaxCalculatorRequestDTO;
import com.imposto.dto.TaxCalculatorResponseDTO;
import com.imposto.service.TaxCalculator.TaxCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taxes/calculate")
public class TaxCalculatorController {

    private static final Logger log = LoggerFactory.getLogger(TaxCalculatorController.class);
    private final TaxCalculatorService taxCalculatorService;

    @Autowired
    public TaxCalculatorController(TaxCalculatorService taxCalculatorService) {
        this.taxCalculatorService = taxCalculatorService;
    }

    @Operation(summary = "calcule tax with base value")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TaxCalculatorResponseDTO> calculateTax (TaxCalculatorRequestDTO taxCalculatorRequestDTO){
        try {
            TaxCalculatorResponseDTO response = taxCalculatorService.calculateTax(taxCalculatorRequestDTO);
            log.info("successful tax calculation with name {} and base value {}", response.getTaxType(), response.getBaseValue());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("error to calculate tax with id {}", taxCalculatorRequestDTO.getTaxId(), e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
