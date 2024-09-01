package com.example.adpotme_api.adotante;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "adotante")
@Entity(name = "Adotante")
@Getter
@Setter
public class Adotante {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;
    private LocalDate dtNasc;
    private String cpf;
    private LocalDateTime cadastro;
    private String email;
    private String senha;
    private String telefone;

    public Adotante(AdotanteCreateDto adotanteCreateDto) {
        this.nome = adotanteCreateDto.getNome();
        this.sobrenome = adotanteCreateDto.getSobrenome();
        this.dtNasc = adotanteCreateDto.getDtNasc();
        this.cpf = adotanteCreateDto.getCpf();
        this.cadastro = adotanteCreateDto.getCadastro();
        this.email = adotanteCreateDto.getEmail();
        this.senha = adotanteCreateDto.getSenha();
        this.telefone = adotanteCreateDto.getTelefone();

    }

    public Adotante() {

    }
}
