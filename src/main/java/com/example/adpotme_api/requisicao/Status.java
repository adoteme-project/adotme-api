package com.example.adpotme_api.requisicao;

public enum Status {
    APROVADO("aprovado"),
    EM_ANDAMENTO("em andamento"),
    REPROVADO("reprovado");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
