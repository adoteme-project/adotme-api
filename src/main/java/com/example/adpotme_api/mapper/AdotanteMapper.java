package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.adotante.AdotanteCreateDto;
import com.example.adpotme_api.dto.adotante.AdotanteResponseDto;
import com.example.adpotme_api.dto.adotante.AdotanteUserDto;
import com.example.adpotme_api.entity.adotante.Adotante;

public class AdotanteMapper {

    public static AdotanteResponseDto toResponseDto(Adotante adotante) {
        return AdotanteResponseDto.builder()
                .id(adotante.getId())
                .nome(adotante.getNome())
                .dtNasc(adotante.getDtNasc())
                .cadastro(adotante.getCadastro())
                .email(adotante.getEmail())
                .senha(adotante.getSenha())
                .celular(adotante.getCelular())
                .endereco(adotante.getEndereco())
                .fotoPerfil(adotante.getFotoPerfil() != null ? adotante.getFotoPerfil().getUrl() : null)
                .formulario(adotante.getFormulario() != null ? FormularioMapper.toResponseDto(adotante.getFormulario()) : null)
                .build();
    }

    public static AdotanteUserDto toAdotanteAutenticadoDto(Adotante adotante) {
        if(adotante == null) {
            return null;
        }

        AdotanteUserDto dto = AdotanteUserDto
                .builder()
                .id(adotante.getId())
                .nome(adotante.getNome())
                .email(adotante.getEmail())
                .build();

        return dto;
    }
}