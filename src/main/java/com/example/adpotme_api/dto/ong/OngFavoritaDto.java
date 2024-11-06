package com.example.adpotme_api.dto.ong;

import com.example.adpotme_api.dto.endereco.EnderecoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OngFavoritaDto {
    public Long ongId;
    public String nome;
    public EnderecoDto endereco;
    public String imagem;

}
