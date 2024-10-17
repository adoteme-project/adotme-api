package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.animal.AnimalOngResponseDto;
import com.example.adpotme_api.entity.animal.Animal;

import java.time.LocalDate;

public class AnimalMapper {
    public static AnimalOngResponseDto toAnimalOngResponseDto(Animal animal) {
        AnimalOngResponseDto animalOngResponseDto = new AnimalOngResponseDto();
        animalOngResponseDto.setId(animal.getId());
        animalOngResponseDto.setImagem(animal.getFotoPerfil().getUrl() != null ? animal.getFotoPerfil().getUrl() : null);
        animalOngResponseDto.setNome(animal.getNome());
        animalOngResponseDto.setIdade(LocalDate.now().getYear() - animal.getAnoNascimento());
        animalOngResponseDto.setPersonalidade(animalOngResponseDto.getPersonalidadeTop2(animal));
        return animalOngResponseDto;

    }
}
