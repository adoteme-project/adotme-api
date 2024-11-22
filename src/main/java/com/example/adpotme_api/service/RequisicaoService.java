package com.example.adpotme_api.service;


import com.example.adpotme_api.dto.requisicao.RequisicaoCreateDto;
import com.example.adpotme_api.dto.requisicao.RequisicaoReadDto;
import com.example.adpotme_api.entity.adocao.LogAdocao;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.entity.requisicao.Status;
import com.example.adpotme_api.mapper.RequisicaoMapper;
import com.example.adpotme_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequisicaoService {
    @Autowired
    private RequisicaoRepository requisicaoRepository;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private AdotanteRepository adotanteRepository;
    @Autowired
    private FormularioRepository formularioRepository;
    @Autowired
    private LogAdocaoRepository logAdocaoRepository;

    public Requisicao criarRequisicao(RequisicaoCreateDto requisicaoCreateDto) {
        Requisicao requisicao = new Requisicao();
        Animal animal = animalRepository.findById(requisicaoCreateDto.getIdAnimal()).orElseThrow();
        Adotante adotante = adotanteRepository.findById(requisicaoCreateDto.getIdAdotante()).orElseThrow();
        requisicao.setAnimal(animal);
        requisicao.setFormulario(adotante.getFormulario());
        requisicao.setStatus(Status.NOVA);
        requisicao.setDataRequisicao(LocalDateTime.now());
        requisicaoRepository.save(requisicao);
        Formulario formulario = adotante.getFormulario();
        formulario.getRequisicao().add(requisicao);
        formularioRepository.save(formulario);
        LogAdocao log = new LogAdocao();
        log.setTipo("aplicação");
        log.setData(LocalDate.now());
        log.setNomeAdotante(adotante.getNome());
        log.setNomeAnimal(animal.getNome());
        log.setOngId(animal.getOng().getId());

        logAdocaoRepository.save(log);


        return requisicao;
    }

    public Requisicao atualizarRequisicaoAprovado(Long id) {
        Requisicao requisicao = requisicaoRepository.findById(id).orElseThrow();
        requisicao.setStatus(Status.APROVADO);
        requisicaoRepository.save(requisicao);

        return requisicao;
    }

    public Requisicao atualizarRequisicaoReprovado(Long id) {
        Requisicao requisicao = requisicaoRepository.findById(id).orElseThrow();
        requisicao.setStatus(Status.DESCARTADO);
        requisicaoRepository.save(requisicao);

        return requisicao;
    }

    public Requisicao atualizarRequisicaoDocumentacao(Long id) {
        Requisicao requisicao = requisicaoRepository.findById(id).orElseThrow();
        requisicao.setStatus(Status.DOCUMENTACAO);
        requisicaoRepository.save(requisicao);

        return requisicao;
    }

    public Requisicao atualizarRequisicaoAdotado(Long id) {
        Requisicao requisicao = requisicaoRepository.findById(id).orElseThrow();
        List<Requisicao> requisicoesDoAnimal = requisicaoRepository.findByAnimal(requisicao.getAnimal());
        Adotante adotante = requisicao.getFormulario().getAdotante();
        Animal animal = requisicao.getAnimal();
        if (animal.getIsAdotado()) {
            throw new RuntimeException("Animal já adotado");
        }
        if(requisicao.getStatus() != Status.APROVADO) {
            throw new RuntimeException("Requisição não está no status aprovado");
        }
        requisicao.setStatus(Status.CONCLUIDO);

        adotante.adotarAnimal(animal);
        for(Requisicao requisicaoDoAnimal : requisicoesDoAnimal) {
            if(requisicaoDoAnimal.getStatus() != Status.CONCLUIDO) {
                requisicaoDoAnimal.setStatus(Status.DESCARTADO);
            }
        }
        LogAdocao log = new LogAdocao();
        log.setTipo("adoção");
        log.setData(LocalDate.now());
        log.setNomeAdotante(adotante.getNome());
        log.setNomeAnimal(animal.getNome());
        log.setOngId(animal.getOng().getId());
        logAdocaoRepository.save(log);
        adotanteRepository.save(adotante);
        animalRepository.save(animal);
        requisicaoRepository.save(requisicao);

        return requisicao;
    }

    public List<RequisicaoReadDto> listarRequisicoes() {
        List<Requisicao> requisicoes = requisicaoRepository.findAll();
        List<RequisicaoReadDto> requisicoesReadDto = new ArrayList<>();
        for(Requisicao requisicao : requisicoes) {
            Formulario formulario = requisicaoRepository.findById(requisicao.getId()).orElseThrow().getFormulario();
            RequisicaoReadDto requisicaoReadDto = RequisicaoMapper.toRequisicaoReadDto(requisicao, formulario);
            requisicoesReadDto.add(requisicaoReadDto);
        }

        return requisicoesReadDto;
    }
}
