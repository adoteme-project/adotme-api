package com.example.adpotme_api.requisicao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequisicaoCreateDto {
    private Status status;
    private Long formularioId;

}
