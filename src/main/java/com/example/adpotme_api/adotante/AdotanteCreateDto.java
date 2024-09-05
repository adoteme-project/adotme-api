package com.example.adpotme_api.adotante;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AdotanteCreateDto {
    @JsonIgnore
    private Long id;
    private String nome;
    private String sobrenome;
    private LocalDate dtNasc;
    private String cpf;
    private LocalDate cadastro = LocalDate.now();
    @Email
    private String email;
    private String senha;
    private String telefone;
}
