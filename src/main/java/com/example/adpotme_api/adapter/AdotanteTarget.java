package com.example.adpotme_api.adapter;

import com.example.adpotme_api.dto.adotante.AdotanteCreateDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AdotanteTarget {
    AdotanteCreateDto request(String adotanteJson) throws JsonProcessingException;
}