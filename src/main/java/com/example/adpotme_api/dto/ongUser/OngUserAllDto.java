package com.example.adpotme_api.dto.ongUser;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class OngUserAllDto {
    private Long id;
    private String nome;
    private String email;
    private LocalDate dataCadastro;
    private String celular;
    private String funcao;

}
