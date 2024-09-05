package com.example.adpotme_api.controller;

import com.example.adpotme_api.adotante.AdotanteRepository;
import com.example.adpotme_api.animal.AnimalRepository;
import com.example.adpotme_api.formulario.Formulario;
import com.example.adpotme_api.formulario.FormularioCreateDto;
import com.example.adpotme_api.formulario.FormularioRepository;
import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.ong.OngCreateDto;
import com.example.adpotme_api.requisicao.Requisicao;
import com.example.adpotme_api.requisicao.RequisicaoRepository;
import com.example.adpotme_api.requisicao.Status;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formularios")
@Tag(name = "Formulario")
public class FormularioController {
    @Autowired
    FormularioRepository formularioRepository;
    @Autowired
    RequisicaoRepository requisicaoRepository;
    @Autowired
    AdotanteRepository adotanteRepository;
    @Autowired
    AnimalRepository animalRepository;

    @PostMapping
    @Transactional

    public ResponseEntity<?> preencherFormulario(@RequestBody @Valid FormularioCreateDto dados) {

        Formulario formulario = new Formulario(dados);

        formulario.setAdotante(adotanteRepository.findById(dados.getAdotanteId()).orElse(null));
        formulario.setAnimal(animalRepository.findById(dados.getAnimalId()).orElse(null));

        Requisicao requisicao = new Requisicao();
        requisicao.setStatus(Status.EM_ANDAMENTO);
        requisicao.setFormulario(formulario);

        formulario.setRequisicao(requisicao);

        formularioRepository.save(formulario);
        requisicaoRepository.save(requisicao);

        return ResponseEntity.status(201).body(formulario);
    }

    @GetMapping
    public ResponseEntity<List<Formulario>> listarFormularios() {
        if(formularioRepository.findAll().isEmpty()) {
            return ResponseEntity.status(204).body(formularioRepository.findAll());
        }
        return ResponseEntity.status(200).body(formularioRepository.findAll());
    }
    @GetMapping("formularios-aceitos")
    public ResponseEntity<List<Formulario>> listarFormulariosAceitos() {

        List<Requisicao> requisicoesAceitas = requisicaoRepository.findAllByStatus(Status.APROVADO);
        if(requisicoesAceitas.isEmpty()) {
            return ResponseEntity.status(204).body(formularioRepository.findAll());
        }

        return ResponseEntity.status(200).body(formularioRepository.findByRequisicaoIn(requisicoesAceitas));
    }
    @GetMapping("formularios-reprovados")
    public ResponseEntity<List<Formulario>> listarFormulariosReprovados(){
        List<Requisicao> requisicoesReprovadas = requisicaoRepository.findAllByStatus(Status.REPROVADO);

        if(requisicoesReprovadas.isEmpty()) {
            return ResponseEntity.status(204).body(formularioRepository.findAll());
        }

        return ResponseEntity.status(201).body(formularioRepository.findByRequisicaoIn(requisicoesReprovadas));
    }
    @GetMapping("formularios-em-andamento")

    public ResponseEntity<List<Formulario>> listarFormulariosEmAndamento(){
        List<Requisicao> requisicoesEmAndamento = requisicaoRepository.findAllByStatus(Status.EM_ANDAMENTO);

        if(requisicoesEmAndamento.isEmpty()) {
            return ResponseEntity.status(204).body(formularioRepository.findAll());
        }
        return ResponseEntity.status(200).body(formularioRepository.findByRequisicaoIn(requisicoesEmAndamento));
    }

    @GetMapping("adotante/{id}")
    public ResponseEntity<List<Formulario>> listarFormulariosPorAdotante(Long id){

      List<Formulario> formularios = formularioRepository.findByAdotanteId(id);

      if(formularios.isEmpty()) {
          return ResponseEntity.status(204).body(formularioRepository.findAll());
      }

      return ResponseEntity.status(200).body(formularios);
    }
}
