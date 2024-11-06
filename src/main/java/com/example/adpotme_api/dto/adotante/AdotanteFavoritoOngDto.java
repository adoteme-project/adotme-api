package com.example.adpotme_api.dto.adotante;

import com.example.adpotme_api.dto.ong.OngFavoritaDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AdotanteFavoritoOngDto {
    private Long usuarioId;
    private List<OngFavoritaDto> ongFavoritas;
}
