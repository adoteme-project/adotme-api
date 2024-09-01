package com.example.adpotme_api.animal;

import jakarta.persistence.Entity;

@Entity
public class Gato extends Animal implements TaxaAdocao{
    public Gato(GatoCreateDto gatoDto) {
        super(gatoDto);
    }
    public Gato() {

    }



    @Override
    public void calcularTaxaAdocao() {
        Double taxa = 30.0;
        if(isCastrado){
            taxa += 20;
        }
        if (isVermifugado) {
            taxa += 20;
        }
        setTaxaAdocao(taxa);
    }


}
