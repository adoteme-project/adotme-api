package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.entity.requisicao.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequisicaoRepository extends JpaRepository<Requisicao, Long> {
    List<Requisicao> findAllByStatus(Status status);

    List<Requisicao> findByAnimal(Animal animalOng);
}
