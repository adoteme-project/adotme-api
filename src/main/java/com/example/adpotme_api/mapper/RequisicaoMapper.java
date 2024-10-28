package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.ongUser.OngUserDto;
import com.example.adpotme_api.dto.requisicao.RequisicaoReadDto;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.entity.requisicaoUser.RequisicaoUserResponsavel;

import java.util.ArrayList;
import java.util.List;

public class RequisicaoMapper {
    public static RequisicaoReadDto toRequisicaoReadDto(Requisicao requisicao, Formulario formulario) {
        if(requisicao == null){
            throw new IllegalArgumentException();
        }
        RequisicaoReadDto requisicaoReadDto = new RequisicaoReadDto();
        List<OngUserDto> ongUsersResponsaveis = new ArrayList<>();
        for(RequisicaoUserResponsavel responsavel : requisicao.getResponsaveis()){
            ongUsersResponsaveis.add(OngUserMapper.toOngUserDto(responsavel));
        }
        requisicaoReadDto.setId(requisicao.getId());
        requisicaoReadDto.setNomeAnimal(requisicao.getAnimal().getNome());
        requisicaoReadDto.setNomeAdotante(formulario.getAdotante().getNome());
        requisicaoReadDto.setResponsaveis(ongUsersResponsaveis);
        requisicaoReadDto.setStatus(requisicao.getStatus());
        return requisicaoReadDto;
    }


}
