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

    public void preencherFormulario(@RequestBody @Valid FormularioCreateDto dados) {


        Formulario formulario = new Formulario(dados);


        formulario.setAdotante(adotanteRepository.findById(dados.getAdotanteId()).orElse(null));
        formulario.setAnimal(animalRepository.findById(dados.getAnimalId()).orElse(null));


        Requisicao requisicao = new Requisicao();
        requisicao.setStatus(Status.EM_ANDAMENTO);
        requisicao.setFormulario(formulario);


        formulario.setRequisicao(requisicao);


        formularioRepository.save(formulario);
        requisicaoRepository.save(requisicao);
    }

    @GetMapping
    public List<Formulario> listarFormularios() {
        return formularioRepository.findAll();
    }
    @GetMapping("listar-aceitos")
    public List<Formulario> listarFormulariosAceitos() {

        List<Requisicao> requisicoesAceitas = requisicaoRepository.findAllByStatus(Status.APROVADO);


        return formularioRepository.findByRequisicaoIn(requisicoesAceitas);
    }
    @GetMapping("listar-reprovados")
    public List<Formulario> listarFormulariosReprovados(){
        List<Requisicao> requisicoesReprovadas = requisicaoRepository.findAllByStatus(Status.REPROVADO);


        return formularioRepository.findByRequisicaoIn(requisicoesReprovadas);
    }
    @GetMapping("listar-em-andamento")

    public List<Formulario> listarFormulariosEmAndamento(){
        List<Requisicao> requisicoesEmAndamento = requisicaoRepository.findAllByStatus(Status.EM_ANDAMENTO);


        return formularioRepository.findByRequisicaoIn(requisicoesEmAndamento);
    }

    @GetMapping("listarPorAdotante")
    public List<Formulario> listarFormulariosPorAdotante(Long id){
        return formularioRepository.findByAdotanteId(id);
    }
}
