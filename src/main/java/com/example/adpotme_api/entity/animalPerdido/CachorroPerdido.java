package com.example.adpotme_api.entity.animalPerdido;

import com.example.adpotme_api.dto.animalPerdido.CachorroPerdidoCreateDto;
import jakarta.persistence.Entity;

@Entity
public class CachorroPerdido extends AnimalPerdido
{


    public CachorroPerdido(CachorroPerdidoCreateDto cachorroPerdidoDto) {
        super(cachorroPerdidoDto);
    }

    public CachorroPerdido() {

    }




}
