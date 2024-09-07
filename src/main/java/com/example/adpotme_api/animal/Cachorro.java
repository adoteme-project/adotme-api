package com.example.adpotme_api.animal;

import jakarta.persistence.Entity;

@Entity
public class Cachorro extends Animal implements TaxaAdocao
{


    public Cachorro(CachorroCreateDto cachorroDto) {
        super(cachorroDto);
    }

    public Cachorro() {

    }

    @Override
    public void calcularTaxaAdocao() {
        Double taxa = 50.0;
        if(isCastrado){
            taxa += 20;
        }
        if (isVermifugado) {
            taxa += 20;
        }
        setTaxaAdocao(taxa);
    }


}