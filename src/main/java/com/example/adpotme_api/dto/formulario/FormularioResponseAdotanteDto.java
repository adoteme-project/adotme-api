package com.example.adpotme_api.dto.formulario;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormularioResponseAdotanteDto {
    private Boolean temCrianca;
    private Boolean moradoresConcordam;
    private Boolean temPet;
    private Boolean seraResponsavel;
    private Boolean moraEmCasa;
    private Boolean isTelado;
    private Boolean casaPortaoAlto;
}
