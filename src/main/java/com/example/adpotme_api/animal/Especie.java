package com.example.adpotme_api.animal;

public enum Especie {
    CACHORRO("cachorro"),
    GATO("gato");

    private final String especie;

    Especie(String especie) {
        this.especie = especie;
    }

    public String getEspecie() {
        return especie;
    }
}
