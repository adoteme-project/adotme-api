package com.example.adpotme_api.dto.animal;

import com.example.adpotme_api.dto.personalidade.PersonalidadeAnimalOngResponseDto;
import com.example.adpotme_api.dto.personalidade.PersonalidadeCreateDto;
import com.example.adpotme_api.entity.animal.Animal;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnimalOngResponseDto {
    private Long id;
    private String nome;
    private Integer idade;
    private String imagem;
    private Integer distancia;
    private String porte;
    private String especie;
    private String sexo;
    private PersonalidadeCreateDto personalidade;



}

