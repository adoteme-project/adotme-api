package com.example.adpotme_api.entity.requisicao;

public enum Status {
    APROVADO("aprovado"),
    INICIO_DA_APLICACAO("em andamento"),
    REPROVADO("reprovado"),
    DOCUMENTACAO("documentação"),
    REVISAO("revisao"),
    ADOTADO("adotado");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
