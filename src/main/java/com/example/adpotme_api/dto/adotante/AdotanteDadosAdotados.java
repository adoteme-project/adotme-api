package com.example.adpotme_api.dto.adotante;

import com.example.adpotme_api.dto.animal.AnimalAdotadoDto;
import com.example.adpotme_api.dto.animal.AnimalCreateDto;
import com.example.adpotme_api.dto.animal.AnimalOngDto;
import com.example.adpotme_api.dto.animal.AnimalOngResponseDto;
import com.example.adpotme_api.entity.animal.Animal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AdotanteDadosAdotados {
    private Long idAdotante;
    private String nome;
    private List<AnimalAdotadoDto> animaisAdotados;
}
