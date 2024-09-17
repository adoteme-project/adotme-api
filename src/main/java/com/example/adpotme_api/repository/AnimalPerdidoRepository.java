package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.animalPerdido.AnimalPerdido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalPerdidoRepository extends JpaRepository<AnimalPerdido, Long> {
}
