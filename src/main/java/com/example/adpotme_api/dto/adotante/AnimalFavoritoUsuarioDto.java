package com.example.adpotme_api.dto.adotante;

import com.example.adpotme_api.dto.animal.AnimalFavoritoDto;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalFavoritoUsuarioDto {
    private Long usuarioId;
    private List<AnimalFavoritoDto> animaisfavoritos;
}
