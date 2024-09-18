package com.example.adpotme_api.dto.animal;

import com.example.adpotme_api.entity.animal.Especie;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AnimalUpdateDto {
    protected String nome;
    protected Integer anoNascimento;
    protected String sexo;
    protected LocalDate dataAbrigo;
    protected Especie especie;
    protected String raca;
    protected Boolean isCastrado;
    protected String descricao;
    protected Boolean isVisible;
    protected Boolean isAdotado;
    protected String porte;
    protected Boolean isVermifugado;
    protected Double taxaAdocao;
    protected Boolean isDestaque;
}
