package com.example.adpotme_api.dto.requisicao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RequisicaoDto {
    private Long id;
    private Long formularioId;
    private String nomeAdotante;
    private String email;
    private LocalDateTime dataRequisicao;
    private String status;
}
