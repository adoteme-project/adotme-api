package com.example.adpotme_api.util;

import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.Animal;
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




