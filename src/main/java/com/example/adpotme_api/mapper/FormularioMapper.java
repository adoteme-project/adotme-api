package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.adotante.AdotanteCreateDto;
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

    public static Formulario toFormulario(AdotanteCreateDto dados){
        Formulario formulario = new Formulario();
        formulario.setTemCrianca(dados.getFormulario().getTemCrianca());
        formulario.setMoradoresConcordam(dados.getFormulario().getMoradoresConcordam());
        formulario.setTemPet(dados.getFormulario().getTemPet());
        formulario.setSeraResponsavel(dados.getFormulario().getSeraResponsavel());
        formulario.setMoraEmCasa(dados.getFormulario().getMoraEmCasa());
        formulario.setIsTelado(dados.getFormulario().getIsTelado());
        formulario.setCasaPortaoAlto(dados.getFormulario().getCasaPortaoAlto());
        return formulario;
    }
}
