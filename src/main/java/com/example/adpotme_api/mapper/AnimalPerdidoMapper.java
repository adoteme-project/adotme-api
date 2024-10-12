package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.animalPerdido.AnimalPerdidoAchadoPerdidoDto;
import com.example.adpotme_api.entity.animalPerdido.AnimalPerdido;

public class AnimalPerdidoMapper {

    public static AnimalPerdidoAchadoPerdidoDto toAnimalPerdidoAchadoPerdidoDto (AnimalPerdido animalPerdido) {

        if(animalPerdido == null) {
            return null;
        }
        return AnimalPerdidoAchadoPerdidoDto.builder()
                .id(animalPerdido.getId())
                .apelido(animalPerdido.getApelido())
                .especie(animalPerdido.getEspecie())
                .genero(animalPerdido.getSexo())
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
}
