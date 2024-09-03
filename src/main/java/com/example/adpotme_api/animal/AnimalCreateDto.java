package com.example.adpotme_api.animal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AnimalCreateDto {
    protected String nome;
    protected Integer anoNascimento;
    protected String sexo;
    protected LocalDate dataAbrigo;
    protected LocalDate cadastro = LocalDate.now();
    protected String especie;
    protected String raca;
    protected Boolean isCastrado;
    protected String descricao;
    protected Boolean isVisible;
    protected Boolean isAdotado;
    protected String porte;
    protected Boolean isVermifugado;
    protected Double taxaAdocao;
    protected Boolean isDestaque;

    @NotNull(message = "ONG não pode ser nulo")
    private Long ongId; // ID da ONG associada
}
