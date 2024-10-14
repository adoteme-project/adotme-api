package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.personalidade.Personalidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalidadeRepository extends JpaRepository<Personalidade, Long> {
}
