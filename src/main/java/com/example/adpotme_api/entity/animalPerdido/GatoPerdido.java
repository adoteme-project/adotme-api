package com.example.adpotme_api.entity.animalPerdido;

import com.example.adpotme_api.dto.animalPerdido.GatoPerdidoCreateDto;
import jakarta.persistence.Entity;

@Entity
public class GatoPerdido extends AnimalPerdido {
    public GatoPerdido(GatoPerdidoCreateDto gatoPerdidoDto) {
        super(gatoPerdidoDto);
    }

    public GatoPerdido() {

    }






}
