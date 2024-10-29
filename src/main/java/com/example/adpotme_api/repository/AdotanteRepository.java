package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.adotante.Adotante;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AdotanteRepository extends JpaRepository<Adotante, Long> {

    Adotante findByEmail(String email);

    boolean existsByEmail(@NotBlank(message = "Email não pode ser vazio") String email);
}
