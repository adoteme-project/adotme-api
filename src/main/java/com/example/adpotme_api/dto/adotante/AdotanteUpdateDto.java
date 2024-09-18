package com.example.adpotme_api.dto.adotante;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdotanteUpdateDto {
    private String nome;
    private String sobrenome;
    private LocalDate dtNasc;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;

}
