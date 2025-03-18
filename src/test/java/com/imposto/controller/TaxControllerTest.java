package com.imposto.controller;

import com.imposto.dto.TaxRequestDTO;
import com.imposto.dto.TaxResponseDTO;
import com.imposto.service.Tax.TaxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaxControllerTest {

    @Mock
    private TaxService taxService;

    @InjectMocks
    private TaxController taxController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetTaxById() {
        Long taxId = 1L;
        TaxResponseDTO responseDTO = new TaxResponseDTO();
        responseDTO.setName("tax 1");
        responseDTO.setId(taxId);
        responseDTO.setAliquota(10.0);

        when(taxService.getTaxById(taxId)).thenReturn(responseDTO);

        ResponseEntity<TaxResponseDTO> response = taxController.getTaxById(taxId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody()); //evita NullPointer
        assertEquals(taxId, response.getBody().getId());
        assertEquals("tax 1", response.getBody().getName());
        assertEquals(10.0, response.getBody().getAliquota());
    }

    @Test
    void shouldReturnBadRequestWhenGettingTaxById() {
        Long taxId = 1L;
        when(taxService.getTaxById(taxId)).thenThrow(new RuntimeException("error getting tax by id:"+taxId));

        ResponseEntity<TaxResponseDTO> response = taxController.getTaxById(taxId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(taxService).getTaxById(taxId);
    }

    @Test
    void shouldGetAllTaxes() {
        TaxResponseDTO tax1 = new TaxResponseDTO();
        tax1.setId(1L);
        tax1.setName("tax 1");
        tax1.setAliquota(10.0);

        TaxResponseDTO tax2 = new TaxResponseDTO();
        tax2.setId(2L);
        tax2.setName("tax 2");
        tax2.setAliquota(15.0);

        List<TaxResponseDTO> taxes = List.of(tax1, tax2);

        when(taxService.getAllTaxs()).thenReturn(taxes);

        ResponseEntity<List<TaxResponseDTO>> response = taxController.getAllTaxes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("tax 1", response.getBody().get(0).getName());
        assertEquals(10.0, response.getBody().get(0).getAliquota());
    }

    @Test
    void shouldReturnBadRequestWhenGettingTaxes() {
        when(taxService.getAllTaxs()).thenThrow(new RuntimeException("error getting taxes"));

        ResponseEntity<List<TaxResponseDTO>> response = taxController.getAllTaxes();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(taxService).getAllTaxs();
    }

    @Test
    void shouldCreateTax() {
        TaxRequestDTO requestDTO = new TaxRequestDTO();
        requestDTO.setName("tax 1");
        requestDTO.setAliquota(10.0);

        TaxResponseDTO responseDTO = new TaxResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("tax 1");
        responseDTO.setAliquota(10.0);

        when(taxService.registerTax(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<TaxResponseDTO> response = taxController.createTax(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("tax 1", response.getBody().getName());
        assertEquals(10.0, response.getBody().getAliquota());
    }

    @Test
    void shouldReturnBadRequestWhenCreatingTaxes() {
        TaxRequestDTO requestDTO = new TaxRequestDTO();
        requestDTO.setName("tax 1");
        requestDTO.setAliquota(10.0);

        when(taxService.registerTax(requestDTO)).thenThrow(new RuntimeException("error creating tax"));

        ResponseEntity<TaxResponseDTO> response = taxController.createTax(requestDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(taxService).registerTax(requestDTO);
    }

    @Test
    void shouldDeleteTaxById() {
        Long taxId = 1L;
        doNothing().when(taxService).deleteTaxById(taxId);

        ResponseEntity<String> response = taxController.deleteTaxById(taxId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("imposto com o id:" + taxId + " foi deletado", response.getBody());

        verify(taxService).deleteTaxById(taxId);
    }

    @Test
    void shouldReturnBadRequestWhenDeletingTax() {
        Long taxId = 1L;
        doThrow(new RuntimeException("error deleting tax")).when(taxService).deleteTaxById(taxId);

        ResponseEntity<String> response = taxController.deleteTaxById(taxId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

        verify(taxService).deleteTaxById(taxId);
    }


}
