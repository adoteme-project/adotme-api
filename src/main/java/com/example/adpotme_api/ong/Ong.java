package com.example.adpotme_api.ong;

import com.example.adpotme_api.animal.Animal;
import com.example.adpotme_api.ongUser.OngUser;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "ong")
@Entity(name = "Ong")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Ong {
    @Setter
    @Getter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    private String nome;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String telefone;
    @Getter
    private String cnpj;
    @Setter
    @Getter
    @OneToMany(mappedBy = "ong", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OngUser> ongUser;
    @OneToMany(mappedBy = "ong", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animal;

    public Ong(OngCreateDto dto) {
        this.nome = dto.getNome();
        this.email = dto.getEmail();
        this.telefone = dto.getTelefone();
        this.cnpj = dto.getCnpj();
    }

}
