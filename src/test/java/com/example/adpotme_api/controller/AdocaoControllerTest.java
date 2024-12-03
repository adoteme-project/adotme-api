package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.adocao.AdocaoDados;
import com.example.adpotme_api.entity.adocao.LogAdocao;
import com.example.adpotme_api.service.AdocaoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdocaoControllerTest {

    @Mock
    private AdocaoService adocaoService;

    @InjectMocks
    private AdocaoController adocaoController;

    public AdocaoControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarAdocoes_Success() {
        Long idOng = 1L;
        LogAdocao logAdocao1 = new LogAdocao();
        logAdocao1.setId(1L);
        logAdocao1.setOngId(idOng);
        logAdocao1.setNomeAdotante("Adotante 1");
        logAdocao1.setNomeAnimal("Animal 1");
        logAdocao1.setTipo("Tipo 1");

        List<LogAdocao> adocoes = Arrays.asList(logAdocao1);

        when(adocaoService.listarAdocoes(idOng)).thenReturn(adocoes);

        ResponseEntity<List<LogAdocao>> response = adocaoController.listarAdocoes(idOng);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(adocaoService, times(1)).listarAdocoes(idOng);
    }

    @Test
    void testListarAdocoes_EmptyList() {
        Long idOng = 1L;

        when(adocaoService.listarAdocoes(idOng)).thenReturn(Arrays.asList());

        ResponseEntity<List<LogAdocao>> response = adocaoController.listarAdocoes(idOng);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(adocaoService, times(1)).listarAdocoes(idOng);
    }

    @Test
    void testListarAdocoesDashboard_Success() {
        Long idOng = 1L;
        Integer ano = 2023;
        AdocaoDados adocaoDados = new AdocaoDados("Janeiro", ano, 100, 50, 0.5);

        List<AdocaoDados> dados = Arrays.asList(adocaoDados);

        when(adocaoService.listarAdocoesDashboard(idOng, ano)).thenReturn(dados);

        ResponseEntity<List<AdocaoDados>> response = adocaoController.listarAdocoesDashboard(idOng, ano);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(adocaoService, times(1)).listarAdocoesDashboard(idOng, ano);
    }
}