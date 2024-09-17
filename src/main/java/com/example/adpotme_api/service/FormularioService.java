package com.example.adpotme_api.service;

import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.dto.formulario.FormularioCreateDto;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.entity.requisicao.Status;
import com.example.adpotme_api.repository.AdotanteRepository;
import com.example.adpotme_api.repository.AnimalRepository;
import com.example.adpotme_api.repository.FormularioRepository;
import com.example.adpotme_api.repository.RequisicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class FormularioService {

    @Autowired
    private FormularioRepository formularioRepository;

    @Autowired
    private RequisicaoRepository requisicaoRepository;

    @Autowired
    private AdotanteRepository adotanteRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Transactional
    public Formulario preencherFormulario(FormularioCreateDto dados) {
        Formulario formulario = new Formulario(dados);

        Adotante adotante = adotanteRepository.findById(dados.getAdotanteId()).orElse(null);
        if (adotante == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }
        formulario.setAdotante(adotante);

        Animal animal = animalRepository.findById(dados.getAnimalId()).orElse(null);
        if (animal == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }
        formulario.setAnimal(animal);

        Requisicao requisicao = new Requisicao();
        requisicao.setStatus(Status.INICIO_DA_APLICACAO);
        requisicao.setFormulario(formulario);

        formulario.setRequisicao(requisicao);

        formularioRepository.save(formulario);
        requisicaoRepository.save(requisicao);

        return formulario;
    }

    public List<Formulario> listarFormularios() {
        return formularioRepository.findAll();
    }

    public List<Formulario> listarFormulariosPorStatus(Status status) {
        List<Requisicao> requisicoes = requisicaoRepository.findAllByStatus(status);
        return formularioRepository.findByRequisicaoIn(requisicoes);
    }

    public List<Formulario> listarFormulariosPorAdotante(Long id) {
        return formularioRepository.findByAdotanteId(id);
    }
}
