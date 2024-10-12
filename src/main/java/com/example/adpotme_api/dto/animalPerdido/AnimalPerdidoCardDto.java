package com.example.adpotme_api.dto.animalPerdido;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AnimalPerdidoCardDto {
    private String apelido;
    private String genero;
    private Long id;
    private String raca;
    private String especie;
    private String porte;
    private String imagem;
    private Double latitude;
    private Double longitude;
    private LocalDate dataResgate;
    private String ruaPerdido;
    private String bairroPerdido;
    private String cidadePerdido;
    private String estadoPerdido;
}
