package com.example.adpotme_api.dto.animalPerdido;

import com.example.adpotme_api.entity.endereco.Endereco;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Setter
@Getter
public class AnimalPerdidoAchadoPerdidoDto {
    private String apelido;
    private String genero;
    private Long id;
    private String raca;
    private String especie;
    private String porte;
    private String imagem;
    private LocalDate dataResgate;
    private String descricao;
    private String nomeOng;
    private String ruaPerdido;
    private String bairroPerdido;
    private String cidadePerdido;
    private String estadoPerdido;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String email;
    private String telefone;
    private Double latitude;
    private Double longitude;

}