package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.animalPerdido.AnimalPerdido;
import com.example.adpotme_api.entity.ong.Ong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalPerdidoRepository extends JpaRepository<AnimalPerdido, Long> {
    List<AnimalPerdido> findByOng(Ong ong);
}
