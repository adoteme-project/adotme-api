package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.entity.requisicao.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormularioRepository extends JpaRepository<Formulario, Long> {
    ;

    List<Formulario> findByAdotanteId(Long id);



}
