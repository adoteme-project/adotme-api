package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.animal.AnimalOngResponseDto;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.util.Localizacao;
import org.springframework.cglib.core.Local;

import java.io.IOException;
import java.time.LocalDate;

public class AnimalMapper {
    public static AnimalOngResponseDto toAnimalOngResponseDto(Animal animal) throws IOException {
        Localizacao localizacao = Localizacao.getLocalizacao();
        AnimalOngResponseDto animalOngResponseDto = new AnimalOngResponseDto();
        animalOngResponseDto.setId(animal.getId());
        animalOngResponseDto.setImagem(animal.getFotoPerfil().getUrl() != null ? animal.getFotoPerfil().getUrl() : null);
        animalOngResponseDto.setNome(animal.getNome());
        animalOngResponseDto.setDistancia(localizacao.calcularDistancia(animal.getOng().getLatitude(), animal.getOng().getLongitude()));
        animalOngResponseDto.setIdade(LocalDate.now().getYear() - animal.getAnoNascimento());
        animalOngResponseDto.setPersonalidade(animalOngResponseDto.getPersonalidadeTop2(animal));
        return animalOngResponseDto;

    }
}
