package com.example.adpotme_api.dto.adotante;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AdotanteDtoListaRequisicao {
    public Long idRequisicao;
    public Long idAnimal;
    public String nomePet;
    public String imagem;
    public LocalDateTime dataAplicacao;
    public String status;
    public String motivoRecusa;
}
