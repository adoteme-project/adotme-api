package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.adotante.Adotante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdotanteRepository extends JpaRepository<Adotante, Long> {

    Adotante findByEmail(String email);
}
