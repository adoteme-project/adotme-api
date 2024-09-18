package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.animalPerdido.AnimalPerdidoUpdateDto;
import com.example.adpotme_api.dto.animalPerdido.CachorroPerdidoCreateDto;
import com.example.adpotme_api.dto.animalPerdido.GatoPerdidoCreateDto;
import com.example.adpotme_api.entity.animalPerdido.*;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.entity.endereco.ViaCepService;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.repository.AnimalPerdidoRepository;
import com.example.adpotme_api.repository.EnderecoRepository;
import com.example.adpotme_api.repository.OngRepository;
import com.example.adpotme_api.util.Sorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalPerdidoService {

    @Autowired
    private AnimalPerdidoRepository animalPerdidoRepository;

    @Autowired
    private OngRepository ongRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Transactional
    public AnimalPerdido cadastrarCachorroPerdido(CachorroPerdidoCreateDto cachorroDto) {
        Optional<Ong> ongOpt = ongRepository.findById(cachorroDto.getOngId());
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ONG não encontrada");
        }
        Ong ong = ongOpt.get();

        Endereco endereco = viaCepService.obterEnderecoPorCep(cachorroDto.getCep());
        enderecoRepository.save(endereco);

        CachorroPerdido cachorroPerdido = new CachorroPerdido(cachorroDto);
        cachorroPerdido.setEnderecoPerdido(endereco);
        cachorroPerdido.setOng(ong);

        return animalPerdidoRepository.save(cachorroPerdido);
    }

    @Transactional
    public AnimalPerdido cadastrarGatoPerdido(GatoPerdidoCreateDto gatoDto) {
        Optional<Ong> ongOpt = ongRepository.findById(gatoDto.getOngId());
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ONG não encontrada");
        }
        Ong ong = ongOpt.get();

        Endereco endereco = viaCepService.obterEnderecoPorCep(gatoDto.getCep());
        enderecoRepository.save(endereco);

        GatoPerdido gatoPerdido = new GatoPerdido(gatoDto);
        gatoPerdido.setEnderecoPerdido(endereco);
        gatoPerdido.setOng(ong);

        return animalPerdidoRepository.save(gatoPerdido);
    }

    public List<AnimalPerdido> recuperarAnimais() {
        return animalPerdidoRepository.findAll();
    }

    public AnimalPerdido recuperarAnimalPorId(Long id) {
        Optional<AnimalPerdido> animalOpt = animalPerdidoRepository.findById(id);
        if (animalOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }
        return animalOpt.get();
    }

    public List<AnimalPerdido> recuperarAnimaisOrdenadosPorEstado() {
        List<AnimalPerdido> animais = animalPerdidoRepository.findAll();
        Sorting.selectionSortAnimalPerdidoByEstado(animais);
        return animais;
    }

    @Transactional
    public CachorroPerdido atualizarCachorroPerdido(Long id, AnimalPerdidoUpdateDto cachorroDto) {
        Optional<AnimalPerdido> animalOpt = animalPerdidoRepository.findById(id);
        if (animalOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }
        AnimalPerdido animal = animalOpt.get();



        CachorroPerdido cachorro = (CachorroPerdido) animal;
        cachorro.setApelido(cachorroDto.getApelido());
        cachorro.setSexo(cachorroDto.getSexo());
        cachorro.setEspecie(cachorroDto.getEspecie());
        cachorro.setIsEncontrado(cachorroDto.getIsEncontrado());
        cachorro.setDescricao(cachorroDto.getDescricao());
        cachorro.setIsVisible(cachorroDto.getIsVisible());
        cachorro.setPorte(cachorroDto.getPorte());
        cachorro.setRaca(cachorroDto.getRaca());


        return animalPerdidoRepository.save(cachorro);
    }

    @Transactional
    public GatoPerdido atualizarGatoPerdido(Long id, AnimalPerdidoUpdateDto gatoDto) {
        Optional<AnimalPerdido> animalOpt = animalPerdidoRepository.findById(id);
        if (animalOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }
        AnimalPerdido animal = animalOpt.get();


        GatoPerdido gato = (GatoPerdido) animal;
        gato.setApelido(gatoDto.getApelido());
        gato.setSexo(gatoDto.getSexo());
        gato.setEspecie(gatoDto.getEspecie());
        gato.setIsEncontrado(gatoDto.getIsEncontrado());
        gato.setDescricao(gatoDto.getDescricao());
        gato.setIsVisible(gatoDto.getIsVisible());
        gato.setPorte(gatoDto.getPorte());
        gato.setRaca(gatoDto.getRaca());


        return animalPerdidoRepository.save(gato);
    }

    public void deletarAnimal(Long id) {
        if (animalPerdidoRepository.existsById(id)) {
            animalPerdidoRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }
    }
}
