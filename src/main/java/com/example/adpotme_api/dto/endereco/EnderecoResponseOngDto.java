package com.example.adpotme_api.dto.endereco;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoResponseOngDto {
    private Long id;
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;


}
