package com.example.adpotme_api.dto.animal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalOngDto {
    private Long id;
    private String nome;
    private Integer idade;
    private String imagem;
    private Integer distancia;
    private String porte;
    private String especie;
}
