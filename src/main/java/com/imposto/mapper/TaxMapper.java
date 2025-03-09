package com.imposto.mapper;

import com.imposto.dto.TaxRequestDTO;
import com.imposto.dto.TaxResponseDTO;
import com.imposto.model.TaxModel;

public class TaxMapper {
    public static TaxModel toEntity(TaxRequestDTO taxRequestDTO){
        return new TaxModel(taxRequestDTO.getName(), taxRequestDTO.getDescription(), taxRequestDTO.getAliquota());
    }
    public static TaxResponseDTO toResppnse(TaxModel taxModel){
        return new TaxResponseDTO(taxModel.getId(), taxModel.getName(), taxModel.getDescription(), taxModel.getAliquota());
    }
}
