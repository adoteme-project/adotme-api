package com.example.adpotme_api.entity.animal;

import com.example.adpotme_api.dto.animal.AnimalCreateDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.strategy.TaxaAdocaoStrategy;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.entity.image.Image;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.entity.personalidade.Personalidade;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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
    protected Especie especie;
    protected String raca;
    protected LocalDate dataAbrigo;
    protected LocalDate cadastro = LocalDate.now();
    protected Boolean isCastrado;
    protected String descricao;
    protected Boolean isVisible;
    protected Boolean isAdotado;
    protected String porte;
    protected Double taxaAdocao;
    @Setter
    @Transient
    @JsonIgnore
    protected TaxaAdocaoStrategy taxaAdocaoStrategy;
    @ManyToOne
    @JoinColumn(name = "adotante_id") // Nome da coluna que mapeia o relacionamento
    @JsonBackReference
    private Adotante adotante;
    @ManyToOne
    @JoinColumn(name = "ong_id")
    @JsonBackReference
    protected Ong ong;
    @OneToMany
    @JoinColumn(name = "foto_perfil_id")
    private List<Image> fotos = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "foto_perfil_id")
    private Image fotoPerfil;
    @OneToOne
    @JoinColumn(name = "personalidade_id")
    private Personalidade personalidade;

    public Long getOngId() {
        return ong != null ? ong.getId() : null;
    }

    public Animal(@Valid AnimalCreateDto animal) {
        this.nome = animal.getNome();
        this.anoNascimento = animal.getAnoNascimento();
        this.sexo = animal.getSexo();
        this.especie = animal.getEspecie();
        this.isCastrado = animal.getIsCastrado();
        this.descricao = animal.getDescricao();
        this.isVisible = animal.getIsVisible();
        this.isAdotado = animal.getIsAdotado();
        this.porte = animal.getPorte();
        this.raca = animal.getRaca();
        this.dataAbrigo = animal.getDataAbrigo();

    }

    public void calcularTaxaAdocao() {
        if (taxaAdocaoStrategy != null) {
            taxaAdocaoStrategy.calcularTaxaAdocao(this);
        }
    }



}