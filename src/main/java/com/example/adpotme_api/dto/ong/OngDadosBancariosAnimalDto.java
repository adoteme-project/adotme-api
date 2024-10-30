package com.example.adpotme_api.dto.ong;

import com.example.adpotme_api.dto.animal.AnimalOngDto;
import com.example.adpotme_api.dto.animal.AnimalOngResponseDto;
import com.example.adpotme_api.dto.endereco.EnderecoResponseOngDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OngDadosBancariosAnimalDto {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cnpj;
    private String banco;
    private String agencia;
    private String conta;
    private String tipoConta;
    private String chavePix;
    private String nomeTitular;
    private String qrCode;
    private EnderecoResponseOngDto endereco;
    private List<AnimalOngDto> animais;
}
