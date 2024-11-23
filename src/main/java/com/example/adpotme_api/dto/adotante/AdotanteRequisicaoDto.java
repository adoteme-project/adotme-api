package com.example.adpotme_api.dto.adotante;

import com.example.adpotme_api.dto.endereco.EnderecoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class AdotanteRequisicaoDto {
    public String nome;
    public Integer idade;
    public String celular;
    public EnderecoDto endereco;
    public String email;
    public Long formularioId;

}
