package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.animalPerdido.AnimalPerdidoAchadoPerdidoDto;
import com.example.adpotme_api.dto.animalPerdido.AnimalPerdidoAchadoPerdidoTabelaDto;
import com.example.adpotme_api.dto.animalPerdido.AnimalPerdidoCardDto;
import com.example.adpotme_api.entity.animalPerdido.AnimalPerdido;

public class AnimalPerdidoMapper {

    public static AnimalPerdidoCardDto toAnimalPerdidoCardDto(AnimalPerdido animalPerdido) {
        if(animalPerdido == null) {
            return null;
        }
        return AnimalPerdidoCardDto.builder()
                .id(animalPerdido.getId())
                .apelido(animalPerdido.getNomePet())
                .especie(animalPerdido.getEspecie())
                .sexo(animalPerdido.getSexo())
                .bairroPerdido(animalPerdido.getEnderecoPerdido().getBairro())
                .cidadePerdido(animalPerdido.getEnderecoPerdido().getCidade())
                .ruaPerdido(animalPerdido.getEnderecoPerdido().getRua())
                .estadoPerdido(animalPerdido.getEnderecoPerdido().getEstado())
                .porte(animalPerdido.getPorte())
                .dataResgate(animalPerdido.getCadastro())
                .imagem(animalPerdido.getFotoPerfil() != null ? animalPerdido.getFotoPerfil().getUrl() : null)
                .latitude(animalPerdido.getLatitude())
                .longitude(animalPerdido.getLongitude())
                .raca(animalPerdido.getRaca())
                .descricao(animalPerdido.getDescricao())
                .rua(animalPerdido.getOng().getEndereco().getRua())
                .bairro(animalPerdido.getOng().getEndereco().getBairro())
                .cidade(animalPerdido.getOng().getEndereco().getCidade())
                .estado(animalPerdido.getOng().getEndereco().getEstado())
                .build();
    }

    public static AnimalPerdidoAchadoPerdidoDto toAnimalPerdidoAchadoPerdidoDto (AnimalPerdido animalPerdido) {

        if(animalPerdido == null) {
            return null;
        }
        return AnimalPerdidoAchadoPerdidoDto.builder()
                .id(animalPerdido.getId())
                .apelido(animalPerdido.getNomePet())
                .especie(animalPerdido.getEspecie())
                .sexo(animalPerdido.getSexo())
                .bairroPerdido(animalPerdido.getEnderecoPerdido().getBairro())
                .cidadePerdido(animalPerdido.getEnderecoPerdido().getCidade())
                .ruaPerdido(animalPerdido.getEnderecoPerdido().getRua())
                .estadoPerdido(animalPerdido.getEnderecoPerdido().getEstado())
                .porte(animalPerdido.getPorte())
                .dataResgate(animalPerdido.getCadastro())
                .imagem(animalPerdido.getFotoPerfil() != null ? animalPerdido.getFotoPerfil().getUrl() : null)
                .descricao(animalPerdido.getDescricao())
                .latitude(animalPerdido.getLatitude())
                .longitude(animalPerdido.getLongitude())
                .raca(animalPerdido.getRaca())
                .nomeOng(animalPerdido.getOng().getNome())
                .email(animalPerdido.getOng().getEmail())
                .telefone(animalPerdido.getOng().getTelefone())
                .rua(animalPerdido.getOng().getEndereco().getRua())
                .bairro(animalPerdido.getOng().getEndereco().getBairro())
                .cidade(animalPerdido.getOng().getEndereco().getCidade())
                .estado(animalPerdido.getOng().getEndereco().getEstado())
                .build();
    }

    public static AnimalPerdidoAchadoPerdidoTabelaDto toAnimalPerdidoOngTabelaDto (AnimalPerdido animalPerdido) {

        if(animalPerdido == null) {
            return null;
        }

        return AnimalPerdidoAchadoPerdidoTabelaDto.builder()
                .id(animalPerdido.getId())
                .apelido(animalPerdido.getNomePet())
                .especie(animalPerdido.getEspecie())
                .sexo(animalPerdido.getSexo())
                .raca(animalPerdido.getRaca())
                .porte(animalPerdido.getPorte())
                .dataResgate(animalPerdido.getCadastro())
                .bairroPerdido(animalPerdido.getEnderecoPerdido().getBairro())
                .cidadePerdido(animalPerdido.getEnderecoPerdido().getCidade())
                .ruaPerdido(animalPerdido.getEnderecoPerdido().getRua())
                .estadoPerdido(animalPerdido.getEnderecoPerdido().getEstado())
                .build();
    }
}
