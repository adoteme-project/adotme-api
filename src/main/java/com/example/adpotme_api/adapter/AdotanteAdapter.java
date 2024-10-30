package com.example.adpotme_api.adapter;

import com.example.adpotme_api.dto.adotante.AdotanteCreateDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AdotanteAdapter {
    AdotanteCreateDto getAdotante(String adotanteJson) throws JsonProcessingException;
}
