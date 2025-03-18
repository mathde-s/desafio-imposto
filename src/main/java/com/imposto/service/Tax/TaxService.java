package com.imposto.service.Tax;

import com.imposto.dto.TaxRequestDTO;
import com.imposto.dto.TaxResponseDTO;
import com.imposto.exceptions.ExistingResourceException;
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
    private final TaxMapper taxMapper;

    @Autowired
    public TaxService(TaxRepository taxRepository, TaxMapper taxMapper){
        this.taxRepository = taxRepository;
        this.taxMapper = taxMapper;
    }

    public TaxResponseDTO registerTax(TaxRequestDTO taxRequestDTO){
        if(taxRepository.existsByName(taxRequestDTO.getName())){
            throw new ExistingResourceException("imposto já existe");
        }
        TaxModel tax = taxMapper.toEntity(taxRequestDTO);
        taxRepository.save(tax);
        return taxMapper.toResponse(tax);
    }

    public List<TaxResponseDTO> getAllTaxs(){
        return taxRepository.findAll()
                .stream()
                .map(taxMapper ::toResponse)
                .collect(Collectors.toList());
    }

    public TaxResponseDTO getTaxById(Long id){
        TaxModel tax = taxRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imposto não existe"));
        return taxMapper.toResponse(tax);
    }

    public void deleteTaxById(Long id) {
        taxRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imposto não existe"));
        taxRepository.deleteById(id);
    }
}
