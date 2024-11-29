package com.example.adpotme_api.entity.ong;

import com.example.adpotme_api.dto.ong.OngCreateDto;
import com.example.adpotme_api.dto.ong.OngUpdateDto;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.dadosBancarios.DadosBancarios;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.entity.image.Image;
import com.example.adpotme_api.entity.ongUser.OngUser;
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
    @Column(unique = true)
    private String email;
    private String celular;
    private String site;
    private String instagram;
    private String facebook;
    private String telefone;
    private String descricao;
    @OneToOne
    private Endereco endereco;
    private String cnpj;
    @OneToMany(mappedBy = "ong", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OngUser> ongUser;
    @JsonManagedReference
    @OneToMany(mappedBy = "ong", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animal;
    @OneToOne(mappedBy = "ong", cascade = CascadeType.ALL, orphanRemoval = true)
    private DadosBancarios dadosBancarios;
    @OneToOne
    private Image imagem;

    public Ong(OngCreateDto dto) {
        this.nome = dto.getNome();
        this.email = dto.getEmail();
        this.telefone = dto.getTelefone();
        this.cnpj = dto.getCnpj();
    }

    public Ong(OngUpdateDto ongAtualizada) {
    }
}
