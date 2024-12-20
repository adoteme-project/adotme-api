package com.example.adpotme_api.dto.animal;

import com.example.adpotme_api.dto.personalidade.PersonalidadeCreateDto;
import com.example.adpotme_api.entity.animal.Especie;
import com.example.adpotme_api.entity.personalidade.Personalidade;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public abstract class AnimalCreateDto {
    protected String nome;
    protected Integer anoNascimento;
    protected String sexo;
    @PastOrPresent
    protected LocalDate dataAbrigo;
    protected Especie especie;
    protected String raca;
    protected Boolean isCastrado;
    protected String descricao;
    protected Boolean isVisible;
    protected Boolean isAdotado;
    protected String porte;
    protected Boolean isVermifugado;
    @PositiveOrZero
    protected Double taxaAdocao;
    protected Boolean isDestaque;
    protected PersonalidadeCreateDto personalidade;

    @NotNull(message = "ONG não pode ser nulo")
    private Long ongId; // ID da ONG associada
}
