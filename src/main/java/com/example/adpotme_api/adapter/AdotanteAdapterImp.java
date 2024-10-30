package com.example.adpotme_api.adapter;

import com.example.adpotme_api.dto.adotante.AdotanteCreateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class AdotanteAdapterImp implements AdotanteAdapter {
    private final ObjectMapper objectMapper;

    public AdotanteAdapterImp() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public AdotanteCreateDto getAdotante(String adotanteJson) throws JsonProcessingException {
        return objectMapper.readValue(adotanteJson, AdotanteCreateDto.class);
    }
}
