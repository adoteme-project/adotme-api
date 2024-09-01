package com.example.adpotme_api.animal;

import jakarta.persistence.Entity;

@Entity
public class Cachorro extends Animal
{


    public Cachorro(CachorroCreateDto cachorroDto) {
        super(cachorroDto);
    }

    public Cachorro() {

    }

    @Override
    public void calcularTaxaAdocao(Animal animal) {
        Double taxa = 50.0;
        if(animal.isCastrado){
            taxa += 20;
        }
        if (animal.isVermifugado) {
            taxa += 20;
        }

        animal.setTaxaAdocao(taxa);
    }
}
