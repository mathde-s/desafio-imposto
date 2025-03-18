package com.imposto.service;

import com.imposto.dto.TaxRequestDTO;
import com.imposto.dto.TaxResponseDTO;
import com.imposto.exceptions.ExistingResourceException;
import com.imposto.exceptions.ResourceNotFoundException;
import com.imposto.mapper.TaxMapper;
import com.imposto.model.TaxModel;
import com.imposto.repository.TaxRepository;
import com.imposto.service.Tax.TaxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaxServiceTest {

    @Mock
    private TaxRepository taxRepository;

    @Mock
    private TaxMapper taxMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    private TaxService taxService;

    @Test
    void shouldRegisterTax() {
        TaxRequestDTO request = new TaxRequestDTO();
        request.setName("test");
        TaxModel model = new TaxModel();
        model.setName("test");
        TaxResponseDTO response = new TaxResponseDTO();
        response.setName("test");

        when(taxRepository.existsByName("test")).thenReturn(false);
        when(taxMapper.toEntity(request)).thenReturn(model);
        when(taxMapper.toResponse(model)).thenReturn(response);

        TaxResponseDTO responseDTO = taxService.registerTax(request);

        assertEquals(request.getName(), responseDTO.getName());
        verify(taxRepository).save(model);
    }

    @Test
    void shouldFailToRegisterTax() {
        TaxRequestDTO request = new TaxRequestDTO();
        request.setName("test");
        when(taxRepository.existsByName(request.getName())).thenReturn(true);

        ExistingResourceException ex = assertThrows(ExistingResourceException.class, () -> {
            taxService.registerTax(request);
        });

        assertEquals("imposto já existe", ex.getMessage());
    }

    @Test
    void shouldGetAllTaxes(){
        TaxModel tax1 = new TaxModel();
        tax1.setId(1L);
        TaxModel tax2 = new TaxModel();
        tax2.setId(2L);

        TaxResponseDTO taxResponse1 = new TaxResponseDTO();
        taxResponse1.setId(1L);
        TaxResponseDTO taxResponse2 = new TaxResponseDTO();
        taxResponse2.setId(2L);

        when(taxRepository.findAll()).thenReturn(List.of(tax1, tax2));
        when(taxMapper.toResponse(tax1)).thenReturn(taxResponse1);
        when(taxMapper.toResponse(tax2)).thenReturn(taxResponse2);

        List<TaxResponseDTO> result = taxService.getAllTaxs();

        verify(taxRepository).findAll();
        verify(taxMapper).toResponse(tax1);
        verify(taxMapper).toResponse(tax2);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    void shouldGetTaxById() {
        Long taxId = 1L;
        TaxModel tax = new TaxModel();
        tax.setId(taxId);

        TaxResponseDTO taxResponse = new TaxResponseDTO();
        taxResponse.setId(taxId);

        when(taxRepository.findById(taxId)).thenReturn(Optional.of(tax));
        when(taxMapper.toResponse(tax)).thenReturn(taxResponse);

        TaxResponseDTO result = taxService.getTaxById(taxId);

        verify(taxRepository).findById(taxId);
        verify(taxMapper).toResponse(tax);
        assertEquals(taxId, result.getId());
    }

    @Test
    void shouldFailToGetTaxById() {
        Long taxId = 1L;
        when(taxRepository.findById(taxId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> taxService.getTaxById(taxId)
        );

        assertEquals("Imposto não existe", exception.getMessage());
        verify(taxRepository).findById(taxId);
        verifyNoInteractions(taxMapper);
        verifyNoMoreInteractions(taxRepository);
    }

    @Test
    void shouldDeleteTaxById() {
        Long taxId = 1L;
        TaxModel tax = new TaxModel();
        tax.setId(taxId);

        when(taxRepository.findById(taxId)).thenReturn(Optional.of(tax));

        taxService.deleteTaxById(taxId);

        verify(taxRepository).findById(taxId);
        verify(taxRepository).deleteById(taxId);
    }

    @Test
    void shouldFailToDeleteTaxById() {
        Long taxId = 1L;
        when(taxRepository.findById(taxId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> taxService.deleteTaxById(taxId)
        );

        assertEquals("Imposto não existe", exception.getMessage());
        verify(taxRepository).findById(taxId);
        verifyNoMoreInteractions(taxRepository);
    }
}
