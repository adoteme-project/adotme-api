package com.example.adpotme_api.util;

import com.example.adpotme_api.dto.ong.OngResponseAllDto;

public class PesquisaBinaria {
    public static int pesquisaBinaria(OngResponseAllDto[] vetor, String nome) {
        int indiceInferior = 0;
        int indiceSuperior = vetor.length - 1;

        while (indiceInferior <= indiceSuperior) {
            int meio = (indiceInferior + indiceSuperior) / 2;
            int comparacao = vetor[meio].getNome().compareToIgnoreCase(nome);

            if (comparacao == 0) {
                System.out.println(vetor[meio]);
                return meio;
            } else if (comparacao > 0) {
                indiceSuperior = meio - 1;
            } else {
                indiceInferior = meio + 1;
            }
        }
        return -1;
    }
}
