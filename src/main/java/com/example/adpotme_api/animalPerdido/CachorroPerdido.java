package com.example.adpotme_api.animalPerdido;

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
