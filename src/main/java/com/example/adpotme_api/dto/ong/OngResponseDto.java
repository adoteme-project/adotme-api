package com.example.adpotme_api.dto.ong;

import com.example.adpotme_api.dto.animal.AnimalOngResponseDto;
import com.example.adpotme_api.dto.endereco.EnderecoResponseOngDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OngResponseDto {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cnpj;
    private EnderecoResponseOngDto endereco;
    private List<AnimalOngResponseDto> animais;

}
