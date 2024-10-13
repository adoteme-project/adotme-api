package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.formulario.FormularioResponseAdotanteDto;
import com.example.adpotme_api.entity.formulario.Formulario;

public class FormularioMapper {
    public static FormularioResponseAdotanteDto toResponseDto(Formulario formulario) {
        FormularioResponseAdotanteDto formularioResponseAdotanteDto = new FormularioResponseAdotanteDto();
        formularioResponseAdotanteDto.setTemCrianca(formulario.getTemCrianca());
        formularioResponseAdotanteDto.setMoradoresConcordam(formulario.getMoradoresConcordam());
        formularioResponseAdotanteDto.setTemPet(formulario.getTemPet());
        formularioResponseAdotanteDto.setSeraResponsavel(formulario.getSeraResponsavel());
        formularioResponseAdotanteDto.setMoraEmCasa(formulario.getMoraEmCasa());
        formularioResponseAdotanteDto.setIsTelado(formulario.getIsTelado());
        formularioResponseAdotanteDto.setCasaPortaoAlto(formulario.getCasaPortaoAlto());
        return formularioResponseAdotanteDto;
    }
}
