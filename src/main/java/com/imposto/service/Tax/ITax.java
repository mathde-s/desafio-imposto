package com.imposto.service.Tax;

import com.imposto.dto.TaxRequestDTO;
import com.imposto.dto.TaxResponseDTO;

import java.util.List;

public interface ITax {
    TaxResponseDTO registerTax(TaxRequestDTO taxRequestDTO);
    List<TaxResponseDTO> getAllTaxs();
    TaxResponseDTO getTaxById();
    void deleteTaxById();
}
