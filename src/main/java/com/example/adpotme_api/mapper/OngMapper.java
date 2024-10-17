package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.animal.AnimalOngResponseDto;
import com.example.adpotme_api.dto.ong.OngResponseDto;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.ong.Ong;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OngMapper {
    public static OngResponseDto toOngResponseDto(Ong ong) throws IOException {
        OngResponseDto ongResponseDto = new OngResponseDto();
        ongResponseDto.setId(ong.getId());
        ongResponseDto.setNome(ong.getNome());
        ongResponseDto.setEmail(ong.getEmail());
        ongResponseDto.setTelefone(ong.getTelefone());
        ongResponseDto.setCnpj(ong.getCnpj());
        ongResponseDto.setEndereco(EnderecoMapper.toEnderecoResponseOngDto(ong.getEndereco()));
        ongResponseDto.setAnimais(toAnimalOngResponseDto(ong.getAnimal()));
        return ongResponseDto;

    }

    public static List<AnimalOngResponseDto> toAnimalOngResponseDto(List<Animal> animais) throws IOException {
        List<AnimalOngResponseDto> animaisResposta = new ArrayList<>();
        for (Animal animal : animais) {
            animaisResposta.add(AnimalMapper.toAnimalOngResponseDto(animal));
        }

        return animaisResposta;
    }
}
