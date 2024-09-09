package com.example.adpotme_api.animal;

import com.example.adpotme_api.animal.strategy.TaxaAdocaoGato;
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
