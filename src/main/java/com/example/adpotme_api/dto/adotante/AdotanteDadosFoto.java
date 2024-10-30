package com.example.adpotme_api.dto.adotante;

import com.example.adpotme_api.dto.endereco.EnderecoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AdotanteDadosFoto {
    private String nome;
    private LocalDate dataNascimeto;
    private String email;
    private String telefone;
    private String urlFoto;
    private EnderecoDto endereco;
}
