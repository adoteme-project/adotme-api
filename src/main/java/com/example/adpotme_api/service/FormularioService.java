package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.adotante.AdotanteFormularioDto;
import com.example.adpotme_api.dto.adotante.AdotanteResponseDto;
import com.example.adpotme_api.dto.formulario.FormularioResponseAdotanteDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.dto.formulario.FormularioCreateDto;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.entity.requisicao.Status;
import com.example.adpotme_api.mapper.AdotanteMapper;
import com.example.adpotme_api.mapper.FormularioMapper;
import com.example.adpotme_api.repository.AdotanteRepository;
import com.example.adpotme_api.repository.AnimalRepository;
import com.example.adpotme_api.repository.FormularioRepository;
import com.example.adpotme_api.repository.RequisicaoRepository;
import jakarta.validation.Valid;
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
    public Formulario preencherFormulario(FormularioCreateDto dados, Long idAdotante) {
        Formulario formulario = new Formulario(dados);

        Adotante adotante = adotanteRepository.findById(idAdotante).orElse(null);
        if (adotante == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }
        formulario.setAdotante(adotante);


        formularioRepository.save(formulario);


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

    public AdotanteResponseDto atualizarFormulario(@Valid FormularioCreateDto dados, Long id) {
        Adotante adotante = adotanteRepository.findById(id).orElse(null);
        if (adotante == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }

        Formulario formulario = adotante.getFormulario();


        formulario.setTemCrianca(dados.getTemCrianca() != null ? dados.getTemCrianca() : formulario.getTemCrianca());
        formulario.setMoradoresConcordam(dados.getMoradoresConcordam() != null ? dados.getMoradoresConcordam() : formulario.getMoradoresConcordam());
        formulario.setTemPet(dados.getTemPet() != null ? dados.getTemPet() : formulario.getTemPet());
        formulario.setSeraResponsavel(dados.getSeraResponsavel() != null ? dados.getSeraResponsavel() : formulario.getSeraResponsavel());
        formulario.setMoraEmCasa(dados.getMoraEmCasa() != null ? dados.getMoraEmCasa() : formulario.getMoraEmCasa());
        formulario.setIsTelado(dados.getIsTelado() != null ? dados.getIsTelado() : formulario.getIsTelado());
        formulario.setCasaPortaoAlto(dados.getCasaPortaoAlto() != null ? dados.getCasaPortaoAlto() : formulario.getCasaPortaoAlto());





        adotante.setFormulario(formulario);
        adotanteRepository.save(adotante);

        formularioRepository.save(formulario);


        return AdotanteMapper.toResponseDto(adotante);
    }

    public AdotanteFormularioDto recuperarAdotanteForm(Long id) {
        Adotante adotante = adotanteRepository.findById(id).orElse(null);
        if (adotante == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }

        return AdotanteMapper.toAdontanteFormularioDto(adotante);


    }

    public FormularioResponseAdotanteDto recuperarFormularioAdotante(Long id) {
        Adotante adotante = adotanteRepository.findById(id).orElse(null);
        if (adotante == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }

        Formulario formulario = adotante.getFormulario();
        if (formulario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Formulário não encontrado");
        }

        return FormularioMapper.toResponseDto(formulario);
    }

    public FormularioResponseAdotanteDto formulariosPorId(Long id) {
        Formulario formulario = formularioRepository.findById(id).orElse(null);
        if (formulario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Formulário não encontrado");
        }
        return FormularioMapper.toResponseDto(formulario);
    }
}
