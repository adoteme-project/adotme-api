package com.example.adpotme_api.dto.requisicao;

import com.example.adpotme_api.entity.requisicao.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequisicaoCreateDto {
    private Status status;
    private Long formularioId;

}
