package com.imposto.service.TaxCalculator;

import com.imposto.dto.TaxCalculatorDTO;
import com.imposto.dto.TaxCalculatorResponseDTO;

public interface ITaxCalculator {
    TaxCalculatorResponseDTO calculateTax(TaxCalculatorDTO calculatorDTO);
}
