package com.example.adpotme_api.entity.animal;

import com.example.adpotme_api.dto.animal.GatoCreateDto;
import com.example.adpotme_api.entity.animal.strategy.TaxaAdocaoGato;
import jakarta.persistence.Entity;

@Entity
public class Gato extends Animal{
    public Gato(GatoCreateDto gatoDto) {
        super(gatoDto);
        setTaxaAdocaoStrategy(new TaxaAdocaoGato());
    }
    public Gato() {
        setTaxaAdocaoStrategy(new TaxaAdocaoGato());
    }







}
