package com.example.adpotme_api.dto.ong;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OngNomeImagemDto {
    private String nome;
    private String imagem;
}
