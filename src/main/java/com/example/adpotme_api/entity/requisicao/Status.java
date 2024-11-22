package com.example.adpotme_api.entity.requisicao;

public enum Status {
    APROVADO("aprovado"),
    NOVA("em andamento"),
    DESCARTADO("descartado"),
    DOCUMENTACAO("documentação"),
    REVISAO("revisao"),
    CONCLUIDO("concluído");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
