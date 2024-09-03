package com.example.adpotme_api.requisicao;

import com.example.adpotme_api.adotante.Adotante;
import com.example.adpotme_api.animal.Animal;
import com.example.adpotme_api.formulario.Formulario;
import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.ongUser.OngUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Table(name = "requisicao")

@Getter
@Setter
public class Requisicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    private Formulario formulario;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToMany
    @JoinTable(
            name = "requisicao_users_responsaveis",
            joinColumns = @JoinColumn(name = "requisicao_id"),
            inverseJoinColumns = @JoinColumn(name = "onguser_id")
    )
    @JsonManagedReference
    private List<OngUser> usersResponsaveis;


    public void adicionarResponsavel(OngUser user) {
        if(!usersResponsaveis.contains(user)){
        usersResponsaveis.add(user);
    }
    }

}
