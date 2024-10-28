package com.example.adpotme_api.service;


import com.example.adpotme_api.dto.requisicao.RequisicaoCreateDto;
import com.example.adpotme_api.dto.requisicao.RequisicaoReadDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.entity.requisicao.Status;
import com.example.adpotme_api.mapper.RequisicaoMapper;
import com.example.adpotme_api.repository.AdotanteRepository;
import com.example.adpotme_api.repository.AnimalRepository;
import com.example.adpotme_api.repository.FormularioRepository;
import com.example.adpotme_api.repository.RequisicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Requisicao criarRequisicao(RequisicaoCreateDto requisicaoCreateDto) {
        Requisicao requisicao = new Requisicao();
        Animal animal = animalRepository.findById(requisicaoCreateDto.getIdAnimal()).orElseThrow();
        Adotante adotante = adotanteRepository.findById(requisicaoCreateDto.getIdAdotante()).orElseThrow();
        requisicao.setAnimal(animal);
        requisicao.setFormulario(adotante.getFormulario());
        requisicao.setStatus(Status.INICIO_DA_APLICACAO);
        requisicaoRepository.save(requisicao);
        Formulario formulario = adotante.getFormulario();
        formulario.getRequisicao().add(requisicao);
        formularioRepository.save(formulario);

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
        requisicao.setStatus(Status.REPROVADO);
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
        requisicao.setStatus(Status.ADOTADO);
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
