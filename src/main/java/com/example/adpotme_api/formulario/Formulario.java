package com.example.adpotme_api.formulario;

import com.example.adpotme_api.adotante.Adotante;
import com.example.adpotme_api.animal.Animal;
import com.example.adpotme_api.ong.OngCreateDto;
import com.example.adpotme_api.requisicao.Requisicao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @ManyToOne
    @JoinColumn(name = "adotante_id")
    @JsonManagedReference
    private Adotante adotante;
    @ManyToOne
    @JoinColumn(name = "animal_id")
    @JsonManagedReference
    private Animal animal;
    @OneToOne(cascade = CascadeType.ALL)

    private Requisicao requisicao;


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
