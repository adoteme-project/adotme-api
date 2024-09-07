package com.example.adpotme_api.controller;

import com.example.adpotme_api.adotante.Adotante;
import com.example.adpotme_api.adotante.AdotanteRepository;
import com.example.adpotme_api.animal.Animal;
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
import java.util.Optional;

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
        Optional<Adotante> adotanteOptional = adotanteRepository.findById(dados.getAdotanteId());

        if (adotanteOptional.isPresent()) {

            formulario.setAdotante(adotanteOptional.get());
        } else {
            return ResponseEntity.status(404).body("Adotante não encontrado");
        }
        Optional<Animal> animalOptional = animalRepository.findById(dados.getAnimalId());

        if(animalOptional.isPresent()){
            formulario.setAnimal(animalOptional.get());
        } else {
            return ResponseEntity.status(404).body("Animal não encontrado");
        }

        Requisicao requisicao = new Requisicao();
        requisicao.setStatus(Status.INICIO_DA_APLICACAO);
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

    public ResponseEntity<List<Formulario>> listarFormulariosInicioAplicacao(){
        List<Requisicao> requisicoesEmAndamento = requisicaoRepository.findAllByStatus(Status.INICIO_DA_APLICACAO);

        if(requisicoesEmAndamento.isEmpty()) {
            return ResponseEntity.status(204).body(formularioRepository.findAll());
        }
        return ResponseEntity.status(200).body(formularioRepository.findByRequisicaoIn(requisicoesEmAndamento));
    }

    public ResponseEntity<List<Formulario>> listarFormularioRevisao(){
        List<Requisicao> requisicoesRevisao = requisicaoRepository.findAllByStatus(Status.REVISAO);

        if(requisicoesRevisao.isEmpty()) {
            return ResponseEntity.status(204).body(formularioRepository.findAll());
        }
        return ResponseEntity.status(200).body(formularioRepository.findByRequisicaoIn(requisicoesRevisao));
    }

    public ResponseEntity<List<Formulario>> listarFormulariosDocumentacao(){
        List<Requisicao> requisicoesEmDocumentacao = requisicaoRepository.findAllByStatus(Status.DOCUMENTACAO);

        if(requisicoesEmDocumentacao.isEmpty()) {
            return ResponseEntity.status(204).body(formularioRepository.findAll());
        }
        return ResponseEntity.status(200).body(formularioRepository.findByRequisicaoIn(requisicoesEmDocumentacao));
    }

    public ResponseEntity<List<Formulario>> listarFormulariosAdotado(){
        List<Requisicao> requisicoesAdotado = requisicaoRepository.findAllByStatus(Status.ADOTADO);

        if(requisicoesAdotado.isEmpty()) {
            return ResponseEntity.status(204).body(formularioRepository.findAll());
        }
        return ResponseEntity.status(200).body(formularioRepository.findByRequisicaoIn(requisicoesAdotado));
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
