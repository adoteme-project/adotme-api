package com.example.adpotme_api.entity.requisicao;

import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.entity.ongUser.OngUser;
import com.example.adpotme_api.entity.requisicaoUser.RequisicaoUserResponsavel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "requisicao")
@Getter
@Setter
public class Requisicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataRequisicao;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "formulario_id")
    private Formulario formulario;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "requisicao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RequisicaoUserResponsavel> responsaveis;
    private String motivoRecusa;

    public void adicionarResponsavel(OngUser user) {
        int controle = 0;
        for (RequisicaoUserResponsavel responsavel : responsaveis) {
            if(responsavel.getOngUser().equals(user)) {
                controle++;
            }
        }

        if(controle == 0) {
            RequisicaoUserResponsavel associacao = new RequisicaoUserResponsavel(this, user);
            responsaveis.add(associacao);
        }


    }

}
