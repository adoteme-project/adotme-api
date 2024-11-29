package com.example.adpotme_api.dto.ong;

import com.example.adpotme_api.dto.dadosBancarios.DadosBancariosCreateDto;
import com.example.adpotme_api.dto.dadosBancarios.DadosBancariosDto;
import com.example.adpotme_api.dto.endereco.EnderecoResponseOngDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OngResponseAllDto {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cnpj;
    private EnderecoResponseOngDto endereco;
    private DadosBancariosDto dadosBancarios;
    private String imagem;
}
