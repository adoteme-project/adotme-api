package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.endereco.EnderecoDto;
import com.example.adpotme_api.dto.endereco.EnderecoResponseOngDto;
import com.example.adpotme_api.entity.endereco.Endereco;

public class EnderecoMapper {
    public static EnderecoResponseOngDto toEnderecoResponseOngDto(Endereco endereco) {
        EnderecoResponseOngDto enderecoResponseOngDto = new EnderecoResponseOngDto();
        enderecoResponseOngDto.setId(endereco.getId());
        enderecoResponseOngDto.setCep(endereco.getCep());
        enderecoResponseOngDto.setRua(endereco.getRua());
        enderecoResponseOngDto.setBairro(endereco.getBairro());
        enderecoResponseOngDto.setCidade(endereco.getCidade());
        enderecoResponseOngDto.setEstado(endereco.getEstado());
        return enderecoResponseOngDto;

    }

    public static EnderecoDto toEnderecoDto(Endereco endereco) {
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setCep(endereco.getCep());
        enderecoDto.setEstado(endereco.getEstado());
        enderecoDto.setCidade(endereco.getCidade());
        enderecoDto.setBairro(endereco.getBairro());
        enderecoDto.setRua(endereco.getRua());
        enderecoDto.setNumero(endereco.getNumero());
        return enderecoDto;
    }
}
