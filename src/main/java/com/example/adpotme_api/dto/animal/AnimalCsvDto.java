package com.example.adpotme_api.dto.animal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnimalCsvDto {
    private Long id;
    private String nome;
    private Integer anoNascimento;
    private String sexo;
    private String especie;
    private String raca;
    private String dataAbrigo;
    private String cadastro;
    private String porte;
    private String descricao;
    private Boolean isCastrado;
    private Boolean isVisible;
    private Boolean isAdotado;
    private Boolean isVermifugado;
    private Double taxaAdocao;
}
