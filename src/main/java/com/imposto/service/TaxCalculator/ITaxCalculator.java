package com.imposto.service.TaxCalculator;

import com.imposto.dto.TaxCalculatorRequestDTO;
import com.imposto.dto.TaxCalculatorResponseDTO;

public interface ITaxCalculator {
    TaxCalculatorResponseDTO calculateTax(TaxCalculatorRequestDTO calculatorDTO);
}
