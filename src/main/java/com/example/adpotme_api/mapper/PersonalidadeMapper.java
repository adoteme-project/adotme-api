package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.personalidade.PersonalidadeAnimalOngResponseDto;
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

    public static PersonalidadeCreateDto toDto (Personalidade personalidade) {
        PersonalidadeCreateDto personalidadeCreateDto = new PersonalidadeCreateDto();
        personalidadeCreateDto.setEnergia(personalidade.getEnergia());
        personalidadeCreateDto.setSociabilidade(personalidade.getSociabilidade());
        personalidadeCreateDto.setTolerante(personalidade.getTolerante());
        personalidadeCreateDto.setObediente(personalidade.getObediente());
        personalidadeCreateDto.setTerritorial(personalidade.getTerritorial());
        personalidadeCreateDto.setInteligencia(personalidade.getInteligencia());
        return personalidadeCreateDto;
    }

    public static PersonalidadeAnimalOngResponseDto toPersonalidadeAnimal(Personalidade personalidade) {
        PersonalidadeAnimalOngResponseDto personalidadeAnimalOngResponseDto = new PersonalidadeAnimalOngResponseDto();
        personalidadeAnimalOngResponseDto.setEnergia(personalidade.getEnergia());
        personalidadeAnimalOngResponseDto.setSociabilidade(personalidade.getSociabilidade());
        personalidadeAnimalOngResponseDto.setTolerante(personalidade.getTolerante());
        personalidadeAnimalOngResponseDto.setObediente(personalidade.getObediente());
        personalidadeAnimalOngResponseDto.setTerritorial(personalidade.getTerritorial());
        personalidadeAnimalOngResponseDto.setInteligencia(personalidade.getInteligencia());
        return personalidadeAnimalOngResponseDto;
    }
}
