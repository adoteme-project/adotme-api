package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.animal.AnimalCsvDto;
import com.example.adpotme_api.dto.animal.AnimalFavoritoDto;
import com.example.adpotme_api.dto.animal.AnimalOngDto;
import com.example.adpotme_api.dto.animal.AnimalOngResponseDto;
import com.example.adpotme_api.entity.animal.Animal;

import java.time.LocalDate;
import java.util.List;

public class AnimalMapper {
    public static AnimalOngResponseDto toAnimalOngResponseDto(Animal animal) {
        AnimalOngResponseDto animalOngResponseDto = new AnimalOngResponseDto();
        animalOngResponseDto.setId(animal.getId());
        animalOngResponseDto.setImagem(animal.getFotoPerfil().getUrl() != null ? animal.getFotoPerfil().getUrl() : null);
        animalOngResponseDto.setNome(animal.getNome());
        animalOngResponseDto.setPorte(animal.getPorte());
        animalOngResponseDto.setSexo(animal.getSexo());
        animalOngResponseDto.setEspecie(animal.getEspecie().getEspecie());
        animalOngResponseDto.setIdade(LocalDate.now().getYear() - animal.getAnoNascimento());
        animalOngResponseDto.setPersonalidade(PersonalidadeMapper.toDto(animal.getPersonalidade()));
        return animalOngResponseDto;

    }
    public static AnimalCsvDto toAnimalCsvDto(Animal animal) {
        AnimalCsvDto animalCsvDto = new AnimalCsvDto();
        animalCsvDto.setId(animal.getId());
        animalCsvDto.setNome(animal.getNome());
        animalCsvDto.setAnoNascimento(animal.getAnoNascimento());
        animalCsvDto.setSexo(animal.getSexo());
        animalCsvDto.setEspecie(animal.getEspecie().getEspecie());
        animalCsvDto.setRaca(animal.getRaca());
        animalCsvDto.setDataAbrigo(animal.getDataAbrigo().toString());
        animalCsvDto.setCadastro(animal.getCadastro().toString());
        animalCsvDto.setPorte(animal.getPorte());
        animalCsvDto.setDescricao(animal.getDescricao());
        animalCsvDto.setIsCastrado(animal.getIsCastrado());
        animalCsvDto.setIsVisible(animal.getIsVisible());
        animalCsvDto.setIsAdotado(animal.getIsAdotado());
        animalCsvDto.setIsVermifugado(animal.getIsVermifugado());
        animalCsvDto.setTaxaAdocao(animal.getTaxaAdocao());
        return animalCsvDto;
    }

    public static AnimalOngDto toAnimalOngDto(Animal animal) {
        AnimalOngDto animalOngDto = new AnimalOngDto();
        animalOngDto.setId(animal.getId());
        animalOngDto.setImagem(animal.getFotoPerfil().getUrl() != null ? animal.getFotoPerfil().getUrl() : null);
        animalOngDto.setNome(animal.getNome());
        animalOngDto.setPorte(animal.getPorte());
        animalOngDto.setEspecie(animal.getEspecie().getEspecie());
        animalOngDto.setIdade(LocalDate.now().getYear() - animal.getAnoNascimento());
        return animalOngDto;
    }

    public static AnimalFavoritoDto toAnimalFavorito(Animal animal){
        return AnimalFavoritoDto.builder()
                .animalId(animal.getId())
                .nome(animal.getNome())
                .idade(LocalDate.now().getYear() - animal.getAnoNascimento())
                .especie(animal.getEspecie().getEspecie())
                .sexo(animal.getSexo())
                .porte(animal.getPorte())
                .distancia(0)
                .imagem(animal.getFotoPerfil().getUrl())
                .build();
    }
}
