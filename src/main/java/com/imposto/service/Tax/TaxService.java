package com.imposto.service.Tax;

import com.imposto.dto.TaxRequestDTO;
import com.imposto.dto.TaxResponseDTO;
import com.imposto.exceptions.ExistingResourceException;
import com.imposto.exceptions.NullArgumentionException;
import com.imposto.exceptions.ResourceNotFoundException;
import com.imposto.mapper.TaxMapper;
import com.imposto.model.TaxModel;
import com.imposto.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxService implements ITax {

    private final TaxRepository taxRepository;

    @Autowired
    public TaxService(TaxRepository taxRepository){
        this.taxRepository = taxRepository;
    }

    public TaxResponseDTO registerTax(TaxRequestDTO taxRequestDTO){
        if(taxRepository.existsByName(taxRequestDTO.getName())){
            throw new ExistingResourceException("imposto já existe");
        }
        TaxModel tax = TaxMapper.toEntity(taxRequestDTO);
        taxRepository.save(tax);
        return TaxMapper.toResppnse(tax);
    }

    public List<TaxResponseDTO> getAllTaxs(){
        return taxRepository.findAll()
                .stream()
                .map(TaxMapper :: toResppnse)
                .collect(Collectors.toList());
    }

    public TaxResponseDTO getTaxById(Long id){
        TaxModel tax = taxRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imposto não existe"));
        return TaxMapper.toResppnse(tax);
    }

    public void deleteTaxById(Long id) {
        taxRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax does not exist."));
        taxRepository.deleteById(id);
    }
}
