package com.example.adpotme_api.util;

import com.example.adpotme_api.adotante.Adotante;
import com.example.adpotme_api.animalPerdido.AnimalPerdido;
import com.example.adpotme_api.ong.Ong;

import java.util.List;

public class Sorting {
    public static void selectionSortAdotanteByEstado(List<Adotante> adotantes) {
        int n = adotantes.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            boolean troca = false;

            for (int j = i + 1; j < n; j++) {
                String estadoAtual = adotantes.get(j).getEndereco().getEstado();
                String estadoMinimo = adotantes.get(minIndex).getEndereco().getEstado();

                if (estadoAtual.compareTo(estadoMinimo) < 0) {
                    minIndex = j;
                    troca = true;
                }
            }


            if (troca) {
                Adotante temp = adotantes.get(minIndex);
                adotantes.set(minIndex, adotantes.get(i));
                adotantes.set(i, temp);
            }
        }
    }

    public static void selectionSortOngByEstado(List<Ong> ongs) {
        int n = ongs.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            boolean troca = false;

            for (int j = i + 1; j < n; j++) {
                String estadoAtual = ongs.get(j).getEndereco().getEstado();
                String estadoMinimo = ongs.get(minIndex).getEndereco().getEstado();

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
                String estadoAtual = animais.get(j).getEnderecoPerdido().getEstado();
                String estadoMinimo = animais.get(minIndex).getEnderecoPerdido().getEstado();

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
