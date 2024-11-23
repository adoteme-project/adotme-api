package com.example.adpotme_api.mapper;


import com.example.adpotme_api.dto.ongUser.OngUserAllDto;
import com.example.adpotme_api.dto.ongUser.OngUserDto;
import com.example.adpotme_api.entity.ongUser.OngUser;
import com.example.adpotme_api.entity.ongUser.Role;
import com.example.adpotme_api.entity.requisicaoUser.RequisicaoUserResponsavel;

public class OngUserMapper {
    public static OngUserDto toOngUserAutenticadoDto(OngUser ongUser) {
        if(ongUser == null) {
            return null;
        }

        return OngUserDto
                .builder()
                .id(ongUser.getId())
                .nome(ongUser.getNome())
                .email(ongUser.getEmail())
                .role(ongUser.getRole())
                .ongId(ongUser.getOng().getId())
                .build();
    }
    public static OngUserDto toOngUserDto(RequisicaoUserResponsavel responsavel){
        if(responsavel == null){
            return null;
        }
        return OngUserDto
                .builder()
                .id(responsavel.getOngUser().getId())
                .nome(responsavel.getOngUser().getNome())
                .email(responsavel.getOngUser().getEmail())
                .role(responsavel.getOngUser().getRole())
                .ongId(responsavel.getOngUser().getOng().getId())
                .build();

    }

    public static OngUserAllDto toOngUserAllDto(OngUser ongUser) {
        if(ongUser == null) {
            return null;
        }

        return OngUserAllDto
                .builder()
                .id(ongUser.getId())
                .nome(ongUser.getNome())
                .celular(ongUser.getCelular())
                .email(ongUser.getEmail())
                .dataCadastro(ongUser.getCadastro())
                .funcao(ongUser.getRole().getRole())
                .build();

    }
}
