package com.example.adpotme_api.entity.requisicao;

public enum Status {
    APROVADO("Aprovado"),
    NOVA("Nova"),
    DESCARTADO("Descartado"),
    DOCUMENTACAO("Documentação"),
    REVISAO("Revisão"),
    CONCLUIDO("Concluído");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
