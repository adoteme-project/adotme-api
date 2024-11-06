package com.example.adpotme_api.dto.adotante;

import com.example.adpotme_api.dto.animal.AnimalFavoritoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class AnimalFavoritoUsuarioDto {
    private Long usuarioId;
    private List<AnimalFavoritoDto> animaisfavoritos;
}
