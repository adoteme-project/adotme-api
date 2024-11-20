package com.example.adpotme_api.dto.animalPerdido;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AnimalPerdidoCreateDto {
    protected String nomePet;
    protected String sexo;
    protected String cep;
    protected LocalDate cadastro = LocalDate.now();
    protected String especie;
    protected PosicaoPetPerdido posicao;
    protected String raca;
    protected String descricao;
    protected Boolean isEncontrado = false;
    protected String porte;
    protected String tamanhoPelo;
    protected Boolean castrado;
    @NotNull(message = "ONG não pode ser nulo")
    private Long ongId; // ID da ONG associada
}
