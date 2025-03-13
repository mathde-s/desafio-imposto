package com.imposto.service.TaxCalculator;

import com.imposto.dto.TaxCalculatorDTO;
import com.imposto.dto.TaxCalculatorResponseDTO;
import com.imposto.exceptions.ResourceNotFoundException;
import com.imposto.model.TaxModel;
import com.imposto.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxCalculatorService implements ITaxCalculator{
    @Autowired
    private TaxRepository taxRepository;

    public TaxCalculatorResponseDTO calculateTax(TaxCalculatorDTO calculatorDTO) {
        TaxModel tax = taxRepository.findById(calculatorDTO.getTaxId())
                .orElseThrow(() -> new ResourceNotFoundException("imposto não encontrado"));
        double TaxValue = (calculatorDTO.getBaseValue() * (tax.getAliquota()) / 100);

        return new TaxCalculatorResponseDTO(
                tax.getName(),
                calculatorDTO.getBaseValue(),
                tax.getAliquota(),
                TaxValue);
    }
}
