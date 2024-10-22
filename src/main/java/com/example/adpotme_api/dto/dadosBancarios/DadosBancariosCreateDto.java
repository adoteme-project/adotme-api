package com.example.adpotme_api.dto.dadosBancarios;

import com.example.adpotme_api.entity.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosBancariosCreateDto {
    private String banco;
    private String agencia;
    private String conta;
    private String tipoConta;
    private String chavePix;
    private String nomeTitular;
}
