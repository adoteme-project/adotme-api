package com.example.adpotme_api.dto.requisicao;

import com.example.adpotme_api.dto.ongUser.OngUserDto;
import com.example.adpotme_api.entity.requisicao.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequisicaoReadDto {
    private Long id;
    private String nomeAdotante;
    private List<OngUserDto> responsaveis;
    private String nomeAnimal;
    private Status status;
}
