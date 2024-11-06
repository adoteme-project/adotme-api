package com.example.adpotme_api.dto.animal;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalFavoritoDto {
    public Long animalId;
    public String nome;
    public Integer idade;
    public String especie;
    public String sexo;
    public String porte;
    public Integer distancia;
    public String imagem;
}
