package com.imposto.controller;

import com.imposto.dto.TaxRequestDTO;
import com.imposto.dto.TaxResponseDTO;
import com.imposto.service.Tax.TaxService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(TaxController.class);

    private final TaxService taxService;

    @Autowired
    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping
    public ResponseEntity<List<TaxResponseDTO>> getAllTaxes(){
        try {
            List<TaxResponseDTO> response = taxService.getAllTaxs();
            log.info("taxes listed successfully. quantity:{}", response.size() );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("error to list taxes", e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TaxResponseDTO> createTax(@RequestBody @Valid TaxRequestDTO requestDTO){
        try {
            TaxResponseDTO response = taxService.registerTax(requestDTO);
            log.info("tax created successfully with id:{} and name:{}", response.getId(), response.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("error to create tax with name:{} ",requestDTO.getName(), e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxResponseDTO> getTaxById(@PathVariable Long id){
        try {
            TaxResponseDTO response = taxService.getTaxById(id);
            log.info("get tax with id:{} successfully",id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("error to get tax with id: {}",id,e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaxById(@PathVariable Long id){
        try {
            taxService.deleteTaxById(id);
            log.info("tax with id:{} deleted successfully ",id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("imposto com o id:" + id + " foi deletado");
        } catch (Exception e) {
            log.error("error to delete tax with id:{}",id,e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
