package com.example.adpotme_api.dto.animalPerdido;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PosicaoPetPerdido {
    protected Double latitude;
    protected Double longitude;
}
