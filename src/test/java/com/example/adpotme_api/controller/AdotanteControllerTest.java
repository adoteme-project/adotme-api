package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.adotante.AdotanteResponseDto;
import com.example.adpotme_api.service.AdotanteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdotanteController.class)
class AdotanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdotanteService adotanteService;

    @Test
    void recuperarAdotantes_quandoNaoHaAdotantes_entaoRetorna204() throws Exception {
        when(adotanteService.recuperarAdotantes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/adotantes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void recuperarAdotantes_quandoHaAdotantes_entaoRetorna200() throws Exception {
        AdotanteResponseDto adotante = new AdotanteResponseDto();
        adotante.setId(1L);
        adotante.setNome("João");
        when(adotanteService.recuperarAdotantes()).thenReturn(Collections.singletonList(adotante));

        mockMvc.perform(get("/adotantes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));
    }
}