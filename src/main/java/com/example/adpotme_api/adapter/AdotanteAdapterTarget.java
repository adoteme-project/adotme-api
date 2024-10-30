package com.example.adpotme_api.adapter;

import com.example.adpotme_api.dto.adotante.AdotanteCreateDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public class AdotanteAdapterTarget implements AdotanteTarget {
    private final AdotanteAdapter adotanteAdapter;

    public AdotanteAdapterTarget(AdotanteAdapter adotanteAdapter) {
        this.adotanteAdapter = adotanteAdapter;
    }

    @Override
    public AdotanteCreateDto request(String adotanteJson) throws JsonProcessingException {
        return adotanteAdapter.getAdotante(adotanteJson);
    }
}