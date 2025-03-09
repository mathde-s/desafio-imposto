package com.imposto.service.Tax;

import com.imposto.dto.TaxRequestDTO;
import com.imposto.dto.TaxResponseDTO;
import com.imposto.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxService implements ITax {

    @Autowired
    private TaxRepository taxRepository;

    public TaxResponseDTO registerTax(TaxRequestDTO taxRequestDTO){
        if(taxRepository.existsByName(taxRequestDTO.getName())){
            throw new ExistingResourceException("imposto já existe");
        }
        TaxModel tax = TaxMapper.toEnity(taxRequestDTO);
        taxRepository.save(tax);
        return TaxMapper.toResppnse(tax);
    }

    public List<TaxResponseDTO> getAllTaxs(){
        return null;
    }

    public TaxResponseDTO getTaxById(){
        return null;
    }

    public void deleteTaxById() {
    }
}
