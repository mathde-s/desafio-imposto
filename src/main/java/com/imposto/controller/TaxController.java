package com.imposto.controller;

import com.imposto.dto.TaxRequestDTO;
import com.imposto.dto.TaxResponseDTO;
import com.imposto.service.Tax.TaxService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taxes")
@Validated
public class TaxController {

    private final TaxService taxService;

    @Autowired
    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping
    public ResponseEntity<List<TaxResponseDTO>> getAllTaxes(){
        return ResponseEntity.status(HttpStatus.OK).body(taxService.getAllTaxs());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TaxResponseDTO> createNewTax(@RequestBody @Valid TaxRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(taxService.registerTax(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxResponseDTO> getTaxById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(taxService.getTaxById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaxById(@PathVariable Long id){
        taxService.deleteTaxById(id);
        return ResponseEntity.status(HttpStatus.OK).body("imposto com o id:" + id + " foi deletado");
    }
}
