package com.example.adpotme_api.dto.animal;

import com.example.adpotme_api.dto.personalidade.PersonalidadeAnimalOngResponseDto;
import com.example.adpotme_api.entity.animal.Animal;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnimalOngResponseDto {
    private Long id;
    private String nome;
    private Integer idade;
    private String imagem;
    private Integer distancia;
    private PersonalidadeAnimalOngResponseDto[] personalidade;

    public PersonalidadeAnimalOngResponseDto[] getPersonalidadeTop2(Animal animal) {
        PersonalidadeAnimalOngResponseDto[] personalidades = new PersonalidadeAnimalOngResponseDto[6];
        PersonalidadeAnimalOngResponseDto p1 = new PersonalidadeAnimalOngResponseDto();
        p1.setPersonalidade("Energia");
        p1.setValor(animal.getPersonalidade().getEnergia());
        personalidades[0] = p1;
        PersonalidadeAnimalOngResponseDto p2 = new PersonalidadeAnimalOngResponseDto();
        p2.setPersonalidade("Sociabilidade");
        p2.setValor(animal.getPersonalidade().getSociabilidade());
        personalidades[1] = p2;
        PersonalidadeAnimalOngResponseDto p3 = new PersonalidadeAnimalOngResponseDto();
        p3.setPersonalidade("Tolerante");
        p3.setValor(animal.getPersonalidade().getTolerante());
        personalidades[2] = p3;
        PersonalidadeAnimalOngResponseDto p4 = new PersonalidadeAnimalOngResponseDto();
        p4.setPersonalidade("Obediente");
        p4.setValor(animal.getPersonalidade().getObediente());
        personalidades[3] = p4;
        PersonalidadeAnimalOngResponseDto p5 = new PersonalidadeAnimalOngResponseDto();
        p5.setPersonalidade("Territorial");
        p5.setValor(animal.getPersonalidade().getTerritorial());
        personalidades[4] = p5;
        PersonalidadeAnimalOngResponseDto p6 = new PersonalidadeAnimalOngResponseDto();
        p6.setPersonalidade("Inteligencia");
        p6.setValor(animal.getPersonalidade().getInteligencia());
        personalidades[5] = p6;

        PersonalidadeAnimalOngResponseDto[] top2 = new PersonalidadeAnimalOngResponseDto[2];
        top2[0] = personalidades[0];
        top2[1] = personalidades[1];
        for (PersonalidadeAnimalOngResponseDto personalidadeAnimalOngResponseDto : personalidades) {
            if (personalidadeAnimalOngResponseDto.getValor() > top2[0].getValor()) {
                top2[1] = top2[0];
                top2[0] = personalidadeAnimalOngResponseDto;
            } else if (personalidadeAnimalOngResponseDto.getValor() > top2[1].getValor()) {
                top2[1] = personalidadeAnimalOngResponseDto;
            }

        }

        return top2;


    }

}

