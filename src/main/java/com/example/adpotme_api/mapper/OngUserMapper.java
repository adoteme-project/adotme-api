package com.example.adpotme_api.mapper;


import com.example.adpotme_api.dto.ongUser.OngUserDto;
import com.example.adpotme_api.entity.ongUser.OngUser;
import com.example.adpotme_api.entity.ongUser.Role;

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
                .build();
    }
}
