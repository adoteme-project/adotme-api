package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.adotante.Adotante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AdotanteRepository extends JpaRepository<Adotante, Long> {

    Optional<Adotante> findByEmail(String email);
}
