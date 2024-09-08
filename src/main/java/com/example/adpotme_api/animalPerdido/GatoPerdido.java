package com.example.adpotme_api.animalPerdido;

import jakarta.persistence.Entity;

@Entity
public class GatoPerdido extends AnimalPerdido {
    public GatoPerdido(GatoPerdidoCreateDto gatoPerdidoDto) {
        super(gatoPerdidoDto);
    }

    public GatoPerdido() {

    }






}
