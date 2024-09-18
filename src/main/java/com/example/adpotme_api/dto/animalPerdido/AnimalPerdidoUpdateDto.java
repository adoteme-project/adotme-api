package com.example.adpotme_api.dto.animalPerdido;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AnimalPerdidoUpdateDto {
    protected String apelido;
    protected String sexo;
    protected String especie;
    protected String raca;
    protected String descricao;
    protected Boolean isVisible;
    protected Boolean isEncontrado;
    protected String porte;
}
