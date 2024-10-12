package com.example.adpotme_api.entity.animalPerdido;

import com.example.adpotme_api.dto.animalPerdido.AnimalPerdidoCreateDto;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.entity.image.Image;
import com.example.adpotme_api.entity.ong.Ong;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "animalperdido")
@Entity(name = "AnimalPerdido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AnimalPerdido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String apelido;
    protected String sexo;
    protected Double latitude;
    protected Double longitude;
    @OneToOne
    protected Endereco enderecoPerdido;
    protected String especie;
    protected String raca;
    protected LocalDate cadastro;
    protected String descricao;
    protected Boolean isVisible;
    protected Boolean isEncontrado;
    protected String porte;
    @ManyToOne
    @JoinColumn(name = "ong_id")
    @JsonBackReference
    protected Ong ong;
    @OneToOne
    @JoinColumn(name = "foto_perfil_id")
    private Image fotoPerfil;


    public Long getOngId() {
        return ong != null ? ong.getId() : null;
    }

    public AnimalPerdido(@Valid AnimalPerdidoCreateDto animal) {
        this.apelido = animal.getApelido();
        this.sexo = animal.getSexo();
        this.especie = animal.getEspecie();
        this.isEncontrado = animal.getIsEncontrado();
        this.descricao = animal.getDescricao();
        this.isVisible = animal.getIsVisible();
        this.porte = animal.getPorte();
        this.cadastro = animal.getCadastro();
        this.raca = animal.getRaca();
        this.latitude = animal.getLatitude();
        this.longitude = animal.getLongitude();

    }

}