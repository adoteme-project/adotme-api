package com.example.adpotme_api.entity.ongUser;

import com.example.adpotme_api.dto.ongUser.OngUserCreateDto;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.entity.requisicao.Status;
import com.example.adpotme_api.entity.requisicaoUser.RequisicaoUserResponsavel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "onguser")
public class OngUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    @Column(unique = true)
    private String email;
    private String senha;
    private LocalDateTime cadastro = LocalDateTime.now();
    private String celular;
    @Enumerated(EnumType.STRING)
    private Role role;


    @ManyToOne
    @JoinColumn(name = "ong_id")
    @JsonBackReference
    private Ong ong;

    @OneToMany(mappedBy = "ongUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RequisicaoUserResponsavel> responsaveis;

    public Long getOngId() {
        return ong != null ? ong.getId() : null;
    }

    public OngUser() {
    }

    public OngUser(OngUserCreateDto dto) {
        this.nome = dto.getNome();
        this.role = Role.valueOf(dto.getRole());
        this.email = dto.getEmail();
        this.senha = dto.getSenha();
        this.celular = dto.getCelular();
    }

    public void rejeitarRequisicao(Requisicao requisicao) {
        requisicao.setStatus(Status.DESCARTADO);
    }

    public void aceitarRequisicao(Requisicao requisicao) {
        requisicao.setStatus(Status.APROVADO);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
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