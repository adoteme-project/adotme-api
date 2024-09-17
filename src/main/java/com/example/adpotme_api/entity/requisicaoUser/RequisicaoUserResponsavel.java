package com.example.adpotme_api.entity.requisicaoUser;

import com.example.adpotme_api.entity.ongUser.OngUser;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "requisicao_users_responsaveis")
@Getter
@Setter
public class RequisicaoUserResponsavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requisicao_id")
    @JsonBackReference
    private Requisicao requisicao;

    @ManyToOne
    @JoinColumn(name = "onguser_id")
    @JsonBackReference
    private OngUser ongUser;

    @Column(name = "data_associacao")
    private LocalDate dataAssociacao;

    // Construtor padrão
    public RequisicaoUserResponsavel() {
    }

    // Construtor com parâmetros
    public RequisicaoUserResponsavel(Requisicao requisicao, OngUser ongUser) {
        this.requisicao = requisicao;
        this.ongUser = ongUser;
        this.dataAssociacao = LocalDate.now();
    }
}