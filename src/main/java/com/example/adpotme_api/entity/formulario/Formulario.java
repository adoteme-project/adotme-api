package com.example.adpotme_api.entity.formulario;

import com.example.adpotme_api.dto.formulario.FormularioCreateDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Table(name = "formulario")
@Entity(name = "Formulario")
public class Formulario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean temCrianca;
    private Boolean moradoresConcordam;
    private Boolean temPet;
    private Boolean seraResponsavel;
    private Boolean moraEmCasa;
    private Boolean isTelado;
    private Boolean casaPortaoAlto;
    @OneToOne
    private Adotante adotante;
    @OneToMany(cascade = CascadeType.ALL)

    private List<Requisicao> requisicao;


    public Formulario(FormularioCreateDto dto) {
        this.temCrianca = dto.getTemCrianca();
        this.moradoresConcordam = dto.getMoradoresConcordam();
        this.temPet = dto.getTemPet();
        this.seraResponsavel = dto.getSeraResponsavel();
        this.moraEmCasa = dto.getMoraEmCasa();
        this.isTelado = dto.getIsTelado();
        this.casaPortaoAlto = dto.getCasaPortaoAlto();

    }

    public Formulario() {

    }
}
