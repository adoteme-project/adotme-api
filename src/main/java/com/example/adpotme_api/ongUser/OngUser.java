package com.example.adpotme_api.ongUser;

import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.requisicao.Requisicao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @ManyToMany(mappedBy = "usersResponsaveis")
    private List<Requisicao> requisicoes;

    public Long getOngId() {
        return ong != null ? ong.getId() : null;
    }
    public OngUser(Long id, Ong ong, String funcao, String cpf, String nome) {
        this.id = id;
        this.ong = ong;
        this.funcao = funcao;
        this.cpf = cpf;
        this.nome = nome;

    }

    public OngUser() {

    }

    public OngUser(OngUserCreateDto dto) {
        this.nome = dto.getNome();
        this.funcao = dto.getFuncao();
        this.cpf = dto.getCpf();

    }

    public void iniciarRevisao(Requisicao requisicao) {
        requisicoes.add(requisicao);
    }


}
