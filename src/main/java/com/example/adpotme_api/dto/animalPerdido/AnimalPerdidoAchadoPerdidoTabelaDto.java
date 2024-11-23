package com.example.adpotme_api.dto.animalPerdido;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Setter
@Getter
public class AnimalPerdidoAchadoPerdidoTabelaDto {
    private String apelido;
    private String sexo;
    private Long id;
    private String raca;
    private String especie;
    private String porte;
    private LocalDate dataResgate;
    private String ruaPerdido;
    private String bairroPerdido;
    private String cidadePerdido;
    private String estadoPerdido;

}