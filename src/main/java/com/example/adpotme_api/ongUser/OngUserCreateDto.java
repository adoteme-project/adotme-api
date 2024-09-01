package com.example.adpotme_api.ongUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OngUserCreateDto {

    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;


    @NotBlank(message = "CPF não pode ser vazio")
    private String cpf;


    @NotBlank(message = "Função não pode ser vazia")
    private String funcao;


    @NotNull(message = "Ong não pode ser nulo")
    private Long ongId; // ID da ONG associada


    public OngUserCreateDto() {
    }


    public void setId(Long id) {
    }
}
