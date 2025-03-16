package com.imposto.mapper;

import com.imposto.dto.TaxRequestDTO;
import com.imposto.dto.TaxResponseDTO;
import com.imposto.model.TaxModel;
import org.springframework.stereotype.Component;

@Component
public class TaxMapper {
    public TaxModel toEntity(TaxRequestDTO taxRequestDTO){
        return new TaxModel(taxRequestDTO.getName(), taxRequestDTO.getDescription(), taxRequestDTO.getAliquota());
    }
    public TaxResponseDTO toResponse(TaxModel taxModel){
        return new TaxResponseDTO(taxModel.getId(), taxModel.getName(), taxModel.getDescription(), taxModel.getAliquota());
    }
}
