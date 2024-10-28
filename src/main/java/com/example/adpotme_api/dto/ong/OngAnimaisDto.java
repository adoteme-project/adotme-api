package com.example.adpotme_api.dto.ong;

import com.example.adpotme_api.entity.animal.Especie;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OngAnimaisDto {
    private Long id;
    private String nome;
    private Especie especie;
    private String raca;
    private Double taxaAdocao;
    private LocalDate dataEntrada;
    private String situacao;
    private Boolean visibilidade;
}
