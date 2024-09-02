package com.example.adpotme_api.requisicao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequisicaoRepository extends JpaRepository<Requisicao, Long> {
    List<Requisicao> findAllByStatus(Status status);
}
