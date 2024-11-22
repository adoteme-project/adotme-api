package com.example.adpotme_api.entity.animal.strategy;

import com.example.adpotme_api.entity.animal.Animal;

public class TaxaAdocaoCachorro implements TaxaAdocaoStrategy{
    @Override
    public void calcularTaxaAdocao(Animal animal) {
        Double taxa = 50.0;
        if(animal.getIsCastrado()){
            taxa += 20;
        }

        animal.setTaxaAdocao(taxa);
    }
}
