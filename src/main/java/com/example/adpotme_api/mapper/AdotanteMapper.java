package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.adotante.AdotanteCreateDto;
import com.example.adpotme_api.dto.adotante.AdotanteResponseDto;
import com.example.adpotme_api.entity.adotante.Adotante;

public class AdotanteMapper {
    public static AdotanteResponseDto toResponseDto(Adotante adotante) {
        AdotanteResponseDto adotanteResponseDto = new AdotanteResponseDto();
        adotanteResponseDto.setId(adotante.getId());
        adotanteResponseDto.setNome(adotante.getNome());
        adotanteResponseDto.setDtNasc(adotante.getDtNasc());
        adotanteResponseDto.setEndereco(adotante.getEndereco());
        adotanteResponseDto.setCadastro(adotante.getCadastro());
        adotanteResponseDto.setEmail(adotante.getEmail());
        adotanteResponseDto.setSenha(adotante.getSenha());
        adotanteResponseDto.setCelular(adotante.getCelular());
        adotanteResponseDto.setFotoPerfil(adotante.getFotoPerfil().getUrl() != null ? adotante.getFotoPerfil().getUrl() : null);
        adotanteResponseDto.setFormulario(adotante.getFormulario() != null ? FormularioMapper.toResponseDto(adotante.getFormulario()) : null);
        return adotanteResponseDto;
    }
}