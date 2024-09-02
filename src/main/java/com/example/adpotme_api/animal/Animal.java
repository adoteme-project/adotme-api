package com.example.adpotme_api.animal;

import com.example.adpotme_api.adotante.Adotante;
import com.example.adpotme_api.formulario.Formulario;
import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.requisicao.Requisicao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Table(name = "animal")
@Entity(name = "Animal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nome;
    protected Integer anoNascimento;
    protected String sexo;
    protected String especie;
    protected Boolean isCastrado;
    protected String descricao;
    protected Boolean isVisible;
    protected Boolean isAdotado;
    protected String porte;
    protected Boolean isVermifugado;
    protected Double taxaAdocao;
    @ManyToOne
    @JoinColumn(name = "adotante_id") // Nome da coluna que mapeia o relacionamento
    @JsonBackReference
    private Adotante adotante;
    @Setter
    @ManyToOne

    @JoinColumn(name = "ong_id")
    @JsonBackReference
    protected Ong ong;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    protected List<Formulario> formulario;


    public Long getOngId() {
        return ong != null ? ong.getId() : null;
    }

    public Animal(@Valid AnimalCreateDto animal) {
        this.nome = animal.getNome();
        this.anoNascimento = animal.getAnoNascimento();
        this.sexo = animal.getSexo();
        this.especie = animal.getEspecie();
        this.isCastrado = false;
        this.descricao = animal.getDescricao();
        this.isVisible = false;
        this.isAdotado = false;
        this.porte = animal.getPorte();
        this.isVermifugado = false;


    }

}