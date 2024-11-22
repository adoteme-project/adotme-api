package com.example.adpotme_api.dto.animal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AnimalAplicacaoDto {
    public Long id;
    public String nome;
    public Integer qtdAplicacoes;
    public LocalDate enviado;
    public Double taxaAdocao;
    public LocalDate dataEntrada;
    public String situacao;


}
