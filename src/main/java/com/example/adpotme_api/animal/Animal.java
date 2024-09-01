package com.example.adpotme_api.animal;

import com.example.adpotme_api.ong.Ong;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Table(name = "animal")
@Entity(name = "Animal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Animal {
@Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nome;
    protected Date nascimento;
    protected String sexo;
    protected String especie;
    protected Boolean isCastrado;
    protected String descricao;
    protected Boolean isVisible;
    protected Boolean isAdotado;
    protected String porte;
    protected Boolean isVermifugado;
    protected Double taxaAdocao;
    @Setter
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ong_id")
    protected Ong ong;


    public Animal(@Valid AnimalCreateDto animal) {
        this.nome = animal.getNome();
        this.nascimento = animal.getNascimento();
        this.sexo = animal.getSexo();
        this.especie = animal.getEspecie();
        this.isCastrado = false;
        this.descricao = animal.getDescricao();
        this.isVisible = false;
        this.isAdotado = false;
        this.porte = animal.getPorte();
        this.isVermifugado = false;

    }

    public abstract void calcularTaxaAdocao(Animal animal);
}
