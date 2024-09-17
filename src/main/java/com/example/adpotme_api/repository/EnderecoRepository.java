package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.endereco.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
