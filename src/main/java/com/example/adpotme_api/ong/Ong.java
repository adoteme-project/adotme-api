package com.example.adpotme_api.ong;

import com.example.adpotme_api.voluntario.Voluntario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "ong")
@Entity(name = "Ong")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Ong {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    @OneToMany(mappedBy = "ong", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Voluntario> voluntarios;

    public Ong(OngCreateDto dto) {
        this.nome = dto.getNome();
        this.email = dto.getEmail();
        this.telefone = dto.getTelefone();
    }


}
