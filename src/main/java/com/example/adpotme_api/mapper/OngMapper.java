package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.animal.AnimalOngResponseDto;
import com.example.adpotme_api.dto.ong.OngAnimaisDto;
import com.example.adpotme_api.dto.ong.OngCreateDto;
import com.example.adpotme_api.dto.ong.OngResponseAllDto;
import com.example.adpotme_api.dto.ong.OngResponseDto;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.ong.Ong;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OngMapper {
    public static OngResponseDto toOngResponseDto(Ong ong) {
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

    public static Ong toEntity(OngCreateDto dto){
        Ong ong = new Ong();
        ong.setNome(dto.getNome());
        ong.setEmail(dto.getEmail());
        ong.setTelefone(dto.getTelefone());
        ong.setCnpj(dto.getCnpj());
        return ong;
    }

    public static OngResponseAllDto toOngResponseAll (Ong ong){
        OngResponseAllDto ongResponseAllDto = new OngResponseAllDto();
        ongResponseAllDto.setId(ong.getId());
        ongResponseAllDto.setNome(ong.getNome());
        ongResponseAllDto.setEmail(ong.getEmail());
        ongResponseAllDto.setTelefone(ong.getTelefone());
        ongResponseAllDto.setCnpj(ong.getCnpj());
        ongResponseAllDto.setEndereco(EnderecoMapper.toEnderecoResponseOngDto(ong.getEndereco()));
        ongResponseAllDto.setDadosBancarios(DadosBancariosMapper.toDto(ong.getDadosBancarios()));
        return ongResponseAllDto;

    }

    public static List<AnimalOngResponseDto> toAnimalOngResponseDto(List<Animal> animais) {
        List<AnimalOngResponseDto> animaisResposta = new ArrayList<>();
        for (Animal animal : animais) {
            animaisResposta.add(AnimalMapper.toAnimalOngResponseDto(animal));
        }

        return animaisResposta;
    }

    public static OngAnimaisDto toOngAnimal(Animal animal){
        OngAnimaisDto ongAnimaisDto = new OngAnimaisDto();
        ongAnimaisDto.setId(animal.getId());
        ongAnimaisDto.setNome(animal.getNome());
        ongAnimaisDto.setEspecie(animal.getEspecie());
        ongAnimaisDto.setRaca(animal.getRaca());
        ongAnimaisDto.setTaxaAdocao(animal.getTaxaAdocao());
        ongAnimaisDto.setDataEntrada(animal.getDataAbrigo());
        ongAnimaisDto.setVisibilidade(animal.getIsVisible());
        return ongAnimaisDto;
    }
}
