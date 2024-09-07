package com.example.adpotme_api.ongUser;

import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.requisicao.Requisicao;
import com.example.adpotme_api.requisicao.Status;
import com.example.adpotme_api.requisicaoUser.RequisicaoUserResponsavel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "onguser")
public class OngUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String funcao;
    private LocalDate cadastro;

    @ManyToOne
    @JoinColumn(name = "ong_id")
    @JsonBackReference
    private Ong ong;

    @OneToMany(mappedBy = "ongUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RequisicaoUserResponsavel> responsaveis;

    public Long getOngId() {
        return ong != null ? ong.getId() : null;
    }

    public OngUser() {
    }

    public OngUser(OngUserCreateDto dto) {
        this.nome = dto.getNome();
        this.funcao = dto.getFuncao();
        this.cpf = dto.getCpf();
    }

    public void rejeitarRequisicao(Requisicao requisicao) {
        requisicao.setStatus(Status.REPROVADO);
    }

    public void aceitarRequisicao(Requisicao requisicao) {
        requisicao.setStatus(Status.APROVADO);
    }




}
