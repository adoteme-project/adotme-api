package com.example.adpotme_api.dto.animal;

import com.example.adpotme_api.entity.animal.Especie;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AnimalUpdateDto {
    protected String nome;
    protected String raca;
    protected Boolean isVisible;
    protected Double taxaAdocao;

}
