package com.example.adpotme_api.dto.adotante;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdotanteUpdateDto {
    private String nome;
    @Past
    private LocalDate dtNasc;
    @Email
    private String email;
    private String senha;
    private String celular;
    private String numero;

}
