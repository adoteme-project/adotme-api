package com.example.adpotme_api.dto.adocao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdocaoDados {
    private String mes;
    private Integer ano;
    private Integer totalAplicacoes;
    private Integer totalAdocoes;
    private Double taxaConversao;

    public AdocaoDados(String mes, Integer ano, Integer totalAplicacoes, Integer totalAdocoes, Double taxaConversao) {
        this.mes = mes;
        this.ano = ano;
        this.totalAplicacoes = totalAplicacoes;
        this.totalAdocoes = totalAdocoes;
        this.taxaConversao = taxaConversao;
    }
}
