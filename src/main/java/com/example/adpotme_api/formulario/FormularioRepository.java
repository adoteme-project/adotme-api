package com.example.adpotme_api.formulario;

import com.example.adpotme_api.requisicao.Requisicao;
import com.example.adpotme_api.requisicao.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormularioRepository extends JpaRepository<Formulario, Long> {
    ;

    List<Formulario> findByAdotanteId(Long id);

    List<Formulario> findByRequisicaoStatus(Status status);

    List<Formulario> findByRequisicaoIn(List<Requisicao> requisicoesAceitas);
}
