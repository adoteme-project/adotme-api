package com.example.adpotme_api.ongUser;

import com.example.adpotme_api.ong.Ong;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "onguser")
public class OngUser {


    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    private String nome;
    @Setter
    @Getter
    private String cpf;
    @Setter
    @Getter
    private String funcao;

    @Setter
    @ManyToOne
    @JoinColumn(name = "ong_id")
    @JsonBackReference
    private Ong ong;

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


}
