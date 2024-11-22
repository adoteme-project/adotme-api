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
import java.util.List;

@Entity
@Table(name = "requisicao")
@Getter
@Setter
public class Requisicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataRequisicao = LocalDate.now();

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    private Formulario formulario;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "requisicao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RequisicaoUserResponsavel> responsaveis;

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
