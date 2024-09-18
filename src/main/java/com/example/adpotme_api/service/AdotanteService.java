package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.adotante.AdotanteUpdateDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.dto.adotante.AdotanteCreateDto;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.entity.endereco.ViaCepService;
import com.example.adpotme_api.repository.AdotanteRepository;
import com.example.adpotme_api.repository.EnderecoRepository;
import com.example.adpotme_api.util.Sorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AdotanteService {

    @Autowired
    private AdotanteRepository adotanteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Transactional
    public Adotante cadastrarAdotante(AdotanteCreateDto dados) {
        Endereco endereco = viaCepService.obterEnderecoPorCep(dados.getCep());
        enderecoRepository.save(endereco);

        Adotante adotante = new Adotante(dados);
        adotante.setEndereco(endereco);

        return adotanteRepository.save(adotante);
    }

    public List<Adotante> recuperarAdotantes() {
        return adotanteRepository.findAll();
    }

    public Adotante recuperarAdotantePorId(Long id) {
        Optional<Adotante> adotanteOpt = adotanteRepository.findById(id);
        if (adotanteOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }
        return adotanteOpt.get();
    }

    public List<Adotante> recuperarAdotantesOrdenadosPorEstado() {
        List<Adotante> adotantes = adotanteRepository.findAll();
        Sorting.selectionSortAdotanteByEstado(adotantes);
        return adotantes;
    }

    @Transactional
    public Adotante atualizarAdotante(Long id, AdotanteUpdateDto adotante) {
        Optional<Adotante> adotanteOpt = adotanteRepository.findById(id);
        if(adotanteOpt.isPresent()){
            Adotante adotanteAtualizado = adotanteOpt.get();
            adotanteAtualizado.setNome(adotante.getNome());
            adotanteAtualizado.setEmail(adotante.getEmail());
            adotanteAtualizado.setCpf(adotante.getCpf());
            adotanteAtualizado.setSobrenome(adotante.getSobrenome());
            adotanteAtualizado.setSenha(adotante.getSenha());
            adotanteAtualizado.setDtNasc(adotante.getDtNasc());
            adotanteAtualizado.setTelefone(adotante.getTelefone());

            return adotanteRepository.save(adotanteAtualizado);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");

    }

    @Transactional
    public void deletarAdotante(Long id) {
        if (!adotanteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }
        adotanteRepository.deleteById(id);
    }
}
