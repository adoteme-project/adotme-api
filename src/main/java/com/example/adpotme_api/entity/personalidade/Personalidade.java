package com.example.adpotme_api.entity.personalidade;

import com.example.adpotme_api.dto.personalidade.PersonalidadeCreateDto;
import com.example.adpotme_api.entity.animal.Animal;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Table(name = "personalidade")
@Entity(name = "Personalidade")
@Getter
@Setter
public class Personalidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(1) @Max(5)
    private Integer energia;
    @Min(1) @Max(5)
    private Integer sociabilidade;
    @Min(1) @Max(5)
    private Integer tolerante;
    @Min(1) @Max(5)
    private Integer obediente;
    @Min(1) @Max(5)
    private Integer territorial;
    @Min(1) @Max(5)
    private Integer inteligencia;


    public Personalidade() {
    }
}
