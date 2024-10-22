package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.dadosBancarios.DadosBancarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosBancariosRepository extends JpaRepository<DadosBancarios, Long> {
}
