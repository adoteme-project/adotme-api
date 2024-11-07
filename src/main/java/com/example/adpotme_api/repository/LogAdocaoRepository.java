package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.adocao.LogAdocao;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogAdocaoRepository extends JpaRepository<LogAdocao, Long> {

    List<LogAdocao> findAllByOngId(Long idOng);
}
