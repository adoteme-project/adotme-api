package com.example.adpotme_api.dto.personalidade;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonalidadeCreateDto {
    private Integer energia;
    private Integer sociabilidade;
    private Integer tolerante;
    private Integer obediente;
    private Integer territorial;
    private Integer inteligencia;

    public PersonalidadeCreateDto(Integer energia, Integer sociabilidade, Integer tolerante, Integer obediente, Integer territorial, Integer inteligencia) {
        this.energia = energia;
        this.sociabilidade = sociabilidade;
        this.tolerante = tolerante;
        this.obediente = obediente;
        this.territorial = territorial;
        this.inteligencia = inteligencia;
    }

    public PersonalidadeCreateDto() {
    }

}
