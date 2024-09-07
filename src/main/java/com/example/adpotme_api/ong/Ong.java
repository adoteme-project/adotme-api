package com.example.adpotme_api.ong;

import com.example.adpotme_api.animal.Animal;
import com.example.adpotme_api.ongUser.OngUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "ong")
@Entity(name = "Ong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Ong {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cnpj;
    @OneToMany(mappedBy = "ong", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OngUser> ongUser;
    @JsonManagedReference
    @OneToMany(mappedBy = "ong", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animal;

    public Ong(OngCreateDto dto) {
        this.nome = dto.getNome();
        this.email = dto.getEmail();
        this.telefone = dto.getTelefone();
        this.cnpj = dto.getCnpj();
    }

}