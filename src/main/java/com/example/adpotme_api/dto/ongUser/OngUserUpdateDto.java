package com.example.adpotme_api.dto.ongUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OngUserUpdateDto {
    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    private String celular;
    private String telefone;

    @NotBlank(message = "Função não pode ser vazia")
    private String funcao;
    @NotBlank(message = "Email não pode ser vazio")
    private String email;
    @NotBlank(message = "Senha não pode ser vazia")
    private String senha;

}
