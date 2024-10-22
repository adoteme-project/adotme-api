package com.example.adpotme_api.util;

import com.example.adpotme_api.dto.adotante.AdotanteResponseDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.animalPerdido.AnimalPerdido;
import com.example.adpotme_api.entity.ong.Ong;

import java.util.List;

public class Sorting {

    public static void quickSortPorNome(List<Ong> ongs) {
        if (ongs != null && ongs.size() > 1) {
            quickSortOngPorNome(ongs, 0, ongs.size() - 1);
        }
    }

    public static void quickSortOngPorNome(List<Ong> ongs, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionOngPorNome(ongs, low, high);
            quickSortOngPorNome(ongs, low, pivotIndex - 1);
            quickSortOngPorNome(ongs, pivotIndex + 1, high);
        }
    }

    private static int partitionOngPorNome(List<Ong> ongs, int low, int high) {
        Ong pivot = ongs.get(high);
        String pivotNome = pivot.getNome();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            Ong currentOng = ongs.get(j);
            String currentNome = currentOng.getNome();

            if (currentNome.compareTo(pivotNome) <= 0) {
                i++;
                Ong temp = ongs.get(i);
                ongs.set(i, ongs.get(j));
                ongs.set(j, temp);
            }
        }

        Ong temp = ongs.get(i + 1);
        ongs.set(i + 1, ongs.get(high));
        ongs.set(high, temp);

        return i + 1;
    }

    public static void quickSortAdotante(List<AdotanteResponseDto> adotantes) {
        if (adotantes != null && adotantes.size() > 1) {
            quickSortAdotanteByEstado(adotantes, 0, adotantes.size() - 1);
        }
    }
    public static void quickSortAdotanteByEstado(List<AdotanteResponseDto> adotantes, int low, int high) {
        if (low < high) {

            int pivotIndex = partition(adotantes, low, high);


            quickSortAdotanteByEstado(adotantes, low, pivotIndex - 1);
            quickSortAdotanteByEstado(adotantes, pivotIndex + 1, high);
        }
    }

    private static int partition(List<AdotanteResponseDto> adotantes, int low, int high) {
        AdotanteResponseDto pivot = adotantes.get(high);
        String pivotEstado = (pivot.getEndereco() != null) ? pivot.getEndereco().getEstado() : "";

        int i = low - 1;

        for (int j = low; j < high; j++) {
            AdotanteResponseDto currentAdotante = adotantes.get(j);
            String currentEstado = (currentAdotante.getEndereco() != null) ? currentAdotante.getEndereco().getEstado() : "";

            if (currentEstado.compareTo(pivotEstado) <= 0) {
                i++;

                AdotanteResponseDto temp = adotantes.get(i);
                adotantes.set(i, adotantes.get(j));
                adotantes.set(j, temp);
            }
        }

        AdotanteResponseDto temp = adotantes.get(i + 1);
        adotantes.set(i + 1, adotantes.get(high));
        adotantes.set(high, temp);

        return i + 1;
    }

    public static Animal[] selectionSortPetPorPersonalidade(List<Animal> animal, String filtro){
        Animal[] animais = new Animal[5];
       switch (filtro){
              case "energia":
                selectionSortPetPorEnergia(animal);

                  for (int i = 0; i < animais.length; i++) {
                        animais[i] = animal.get(i);

                  }
                  return animais;

              case "sociabilidade":
                selectionSortPetPorSociabilidade(animal);

                  for (int i = 0; i < animais.length; i++) {
                      animais[i] = animal.get(i);

                  }
                  return animais;

              case "tolerante":
                selectionSortPetPorTolerante(animal);

                  for (int i = 0; i < animais.length; i++) {
                      animais[i] = animal.get(i);

                  }
                  return animais;

              case "obediente":
                selectionSortPetPorObediente(animal);

                  for (int i = 0; i < animais.length; i++) {
                      animais[i] = animal.get(i);

                  }
                  return animais;
              case "territorial":
                selectionSortPetPorTerritorial(animal);

                  for (int i = 0; i < animais.length; i++) {
                      animais[i] = animal.get(i);

                  }
                  return animais;

              case "inteligencia":
                selectionSortPetPorInteligencia(animal);

                  for (int i = 0; i < animais.length; i++) {
                      animais[i] = animal.get(i);

                  }
                  return animais;

       }

       return null;
        }

    private static void selectionSortPetPorInteligencia(List<Animal> animal) {
        int n = animal.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            boolean troca = false;

            for (int j = i + 1; j < n; j++) {
                Animal animalAtual = animal.get(j);
                Animal animalMinimo = animal.get(minIndex);

                if (animalAtual.getPersonalidade().getInteligencia() > animalMinimo.getPersonalidade().getInteligencia()) {
                    minIndex = j;
                    troca = true;
                }
            }

            if (troca) {
                Animal temp = animal.get(minIndex);
                animal.set(minIndex, animal.get(i));
                animal.set(i, temp);
            }
        }
    }

    private static void selectionSortPetPorTerritorial(List<Animal> animal) {
        int n = animal.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            boolean troca = false;

            for (int j = i + 1; j < n; j++) {
                Animal animalAtual = animal.get(j);
                Animal animalMinimo = animal.get(minIndex);

                if (animalAtual.getPersonalidade().getTerritorial() > animalMinimo.getPersonalidade().getTerritorial()) {
                    minIndex = j;
                    troca = true;
                }
            }

            if (troca) {
                Animal temp = animal.get(minIndex);
                animal.set(minIndex, animal.get(i));
                animal.set(i, temp);
            }
        }
    }

    private static void selectionSortPetPorObediente(List<Animal> animal) {
        int n = animal.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            boolean troca = false;

            for (int j = i + 1; j < n; j++) {
                Animal animalAtual = animal.get(j);
                Animal animalMinimo = animal.get(minIndex);

                if (animalAtual.getPersonalidade().getObediente() > animalMinimo.getPersonalidade().getObediente()) {
                    minIndex = j;
                    troca = true;
                }
            }

            if (troca) {
                Animal temp = animal.get(minIndex);
                animal.set(minIndex, animal.get(i));
                animal.set(i, temp);
            }
        }
    }

    private static void selectionSortPetPorTolerante(List<Animal> animal) {
        int n = animal.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            boolean troca = false;

            for (int j = i + 1; j < n; j++) {
                Animal animalAtual = animal.get(j);
                Animal animalMinimo = animal.get(minIndex);

                if (animalAtual.getPersonalidade().getTolerante() > animalMinimo.getPersonalidade().getTolerante()) {
                    minIndex = j;
                    troca = true;
                }
            }

            if (troca) {
                Animal temp = animal.get(minIndex);
                animal.set(minIndex, animal.get(i));
                animal.set(i, temp);
            }
        }
    }

    private static void selectionSortPetPorSociabilidade(List<Animal> animal) {
        int n = animal.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            boolean troca = false;

            for (int j = i + 1; j < n; j++) {
                Animal animalAtual = animal.get(j);
                Animal animalMinimo = animal.get(minIndex);

                if (animalAtual.getPersonalidade().getSociabilidade() > animalMinimo.getPersonalidade().getSociabilidade()) {
                    minIndex = j;
                    troca = true;
                }
            }

            if (troca) {
                Animal temp = animal.get(minIndex);
                animal.set(minIndex, animal.get(i));
                animal.set(i, temp);
            }
        }
    }

    public static void selectionSortPetPorEnergia(List<Animal> animal) {
            int n = animal.size();
            for (int i = 0; i < n - 1; i++) {
                int minIndex = i;
                boolean troca = false;

                for (int j = i + 1; j < n; j++) {
                    Animal animalAtual = animal.get(j);
                    Animal animalMinimo = animal.get(minIndex);

                    if (animalAtual.getPersonalidade().getEnergia() > animalMinimo.getPersonalidade().getEnergia()) {
                        minIndex = j;
                        troca = true;
                    }
                }

                if (troca) {
                    Animal temp = animal.get(minIndex);
                    animal.set(minIndex, animal.get(i));
                    animal.set(i, temp);
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




