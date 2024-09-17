package com.example.adpotme_api.dto.formulario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormularioCreateDto {
    private Boolean temCrianca;
    private Boolean moradoresConcordam;
    private Boolean temPet;
    private Boolean seraResponsavel;
    private Boolean moraEmCasa;
    private Boolean isTelado;
    private Boolean casaPortaoAlto;
    private Long adotanteId;
    private Long animalId;
    @JsonIgnore
    private Long requisicaoId;


}

