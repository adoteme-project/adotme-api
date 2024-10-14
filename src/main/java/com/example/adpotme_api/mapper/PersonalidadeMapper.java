package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.personalidade.PersonalidadeCreateDto;
import com.example.adpotme_api.entity.personalidade.Personalidade;

public class PersonalidadeMapper {
    public static Personalidade toEntity (PersonalidadeCreateDto personalidadeCreateDto) {
        Personalidade personalidade = new Personalidade();
        personalidade.setEnergia(personalidadeCreateDto.getEnergia());
        personalidade.setSociabilidade(personalidadeCreateDto.getSociabilidade());
        personalidade.setTolerante(personalidadeCreateDto.getTolerante());
        personalidade.setObediente(personalidadeCreateDto.getObediente());
        personalidade.setTerritorial(personalidadeCreateDto.getTerritorial());
        personalidade.setInteligencia(personalidadeCreateDto.getInteligencia());
        return personalidade;
    }
}
