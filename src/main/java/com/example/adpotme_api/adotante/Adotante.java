package com.example.adpotme_api.adotante;

import com.example.adpotme_api.animal.Animal;
import com.example.adpotme_api.formulario.Formulario;
import com.example.adpotme_api.requisicao.Requisicao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDate cadastro;
    private String email;
    private String senha;
    private String telefone;
    @OneToMany(mappedBy = "adotante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Formulario> formulario;
    @OneToMany(mappedBy = "adotante") // O nome aqui deve corresponder ao nome da propriedade na classe Animal
    private List<Animal> adotados;

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

    public void adotarAnimal(Animal animal) {
        animal.setAdotante(this);
        this.adotados.add(animal);

    }
}
