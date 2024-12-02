package com.example.adpotme_api.dto.ongUser;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OngUserEditDto {
    public String nome;
    public String telefone;
    public String celular;
    public String email;
    public String funcao;
}
