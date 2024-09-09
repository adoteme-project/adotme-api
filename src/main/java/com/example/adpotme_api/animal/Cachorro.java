package com.example.adpotme_api.animal;

import com.example.adpotme_api.animal.strategy.TaxaAdocaoCachorro;
import jakarta.persistence.Entity;

@Entity
public class Cachorro extends Animal
{


    public Cachorro(CachorroCreateDto cachorroDto) {
        super(cachorroDto);
        setTaxaAdocaoStrategy(new TaxaAdocaoCachorro());
    }

    public Cachorro() {
        setTaxaAdocaoStrategy(new TaxaAdocaoCachorro());
    }




}
