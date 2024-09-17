package com.example.adpotme_api.util;

import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animalPerdido.AnimalPerdido;
import com.example.adpotme_api.entity.ong.Ong;

import java.util.List;

public class Sorting {
    public static void selectionSortAdotanteByEstado(List<Adotante> adotantes) {
        int n = adotantes.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                Adotante adotanteI = adotantes.get(i);
                Adotante adotanteJ = adotantes.get(j);

                String estadoI = (adotanteI.getEndereco() != null) ? adotanteI.getEndereco().getEstado() : "";
                String estadoJ = (adotanteJ.getEndereco() != null) ? adotanteJ.getEndereco().getEstado() : "";

                if (estadoI.compareTo(estadoJ) > 0) {

                    Adotante temp = adotantes.get(i);
                    adotantes.set(i, adotantes.get(j));
                    adotantes.set(j, temp);
                }
            }
        }
    }

    public static void selectionSortOngByEstado(List<Ong> ongs) {
        int n = ongs.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            boolean troca = false;

            for (int j = i + 1; j < n; j++) {
                Ong ongAtual = ongs.get(j);
                Ong ongMinimo = ongs.get(minIndex);

                String estadoAtual = (ongAtual.getEndereco() != null) ? ongAtual.getEndereco().getEstado() : "";
                String estadoMinimo = (ongMinimo.getEndereco() != null) ? ongMinimo.getEndereco().getEstado() : "";

                if (estadoAtual.compareTo(estadoMinimo) < 0) {
                    minIndex = j;
                    troca = true;
                }
            }

            if (troca) {
                Ong temp = ongs.get(minIndex);
                ongs.set(minIndex, ongs.get(i));
                ongs.set(i, temp);
            }
        }
    }

    public static void selectionSortAnimalPerdidoByEstado(List<AnimalPerdido> animais) {
        int n = animais.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            boolean troca = false;

            for (int j = i + 1; j < n; j++) {
                AnimalPerdido animalAtual = animais.get(j);
                AnimalPerdido animalMinimo = animais.get(minIndex);

                String estadoAtual = (animalAtual.getEnderecoPerdido() != null) ? animalAtual.getEnderecoPerdido().getEstado() : "";
                String estadoMinimo = (animalMinimo.getEnderecoPerdido() != null) ? animalMinimo.getEnderecoPerdido().getEstado() : "";

                if (estadoAtual.compareTo(estadoMinimo) < 0) {
                    minIndex = j;
                    troca = true;
                }
            }

            if (troca) {
                AnimalPerdido temp = animais.get(minIndex);
                animais.set(minIndex, animais.get(i));
                animais.set(i, temp);
            }
        }
    }

}
