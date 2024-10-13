package com.example.adpotme_api.entity.adotante;

import com.example.adpotme_api.dto.adotante.AdotanteCreateDto;
import com.example.adpotme_api.dto.adotante.AdotanteUpdateDto;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.entity.image.Image;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Table(name = "adotante")
@Entity(name = "Adotante")
@Getter
@Setter
public class Adotante implements UserDetails {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=3, max=150)
    private String nome;

    @Past
    private LocalDate dtNasc;

    @OneToOne
    private Endereco endereco;
    private LocalDate cadastro;

    @Column(unique = true)
    private String email;

    @Size(min=8)
    private String senha;
    private String celular;

    @OneToOne(mappedBy = "adotante")
    @JsonManagedReference
    private Formulario formulario;

    @OneToMany(mappedBy = "adotante") // O nome aqui deve corresponder ao nome da propriedade na classe Animal
    private List<Animal> adotados;

    @OneToOne
    @JoinColumn(name = "foto_perfil_id")
    private Image fotoPerfil;

    public Adotante(AdotanteCreateDto adotanteCreateDto) {
        this.nome = adotanteCreateDto.getNome();
        this.dtNasc = adotanteCreateDto.getDtNasc();
        this.cadastro = adotanteCreateDto.getCadastro();
        this.email = adotanteCreateDto.getEmail();
        this.senha = adotanteCreateDto.getSenha();
        this.celular = adotanteCreateDto.getCelular();
    }

    public Adotante() {}

    public void adotarAnimal(Animal animal) {
        animal.setAdotante(this);
        this.adotados.add(animal);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADOTANTE"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
