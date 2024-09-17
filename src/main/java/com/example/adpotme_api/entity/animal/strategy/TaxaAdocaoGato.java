package com.example.adpotme_api.entity.animal.strategy;

import com.example.adpotme_api.entity.animal.Animal;

public class TaxaAdocaoGato implements TaxaAdocaoStrategy {
    public void calcularTaxaAdocao(Animal animal) {
        Double taxa = 30.0;
        if(animal.getIsCastrado()){
            taxa += 20;
        }
        if (animal.getIsVermifugado()) {
            taxa += 20;
        }
        animal.setTaxaAdocao(taxa);
    }
}
