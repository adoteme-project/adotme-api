package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.animalPerdido.*;
import com.example.adpotme_api.entity.animalPerdido.*;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.entity.endereco.ViaCepService;
import com.example.adpotme_api.entity.image.Image;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.integration.CloudinaryService;
import com.example.adpotme_api.mapper.AnimalPerdidoMapper;
import com.example.adpotme_api.repository.AnimalPerdidoRepository;
import com.example.adpotme_api.repository.EnderecoRepository;
import com.example.adpotme_api.repository.OngRepository;
import com.example.adpotme_api.util.Sorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional
    public AnimalPerdido cadastrarCachorroPerdido(CachorroPerdidoCreateDto cachorroDto, MultipartFile fotoPerfil) {
        Optional<Ong> ongOpt = ongRepository.findById(cachorroDto.getOngId());
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ONG não encontrada");
        }
        Ong ong = ongOpt.get();
        CachorroPerdido cachorro = new CachorroPerdido(cachorroDto);

        if(fotoPerfil != null && !fotoPerfil.isEmpty()) {
            try {
                Image image = cloudinaryService.upload(fotoPerfil);
                cachorro.setFotoPerfil(image);
                Endereco endereco = viaCepService.obterEnderecoPorCep(cachorroDto.getCep());
                enderecoRepository.save(endereco);

                cachorro.setEnderecoPerdido(endereco);
                cachorro.setOng(ong);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        return animalPerdidoRepository.save(cachorro);
    }

    @Transactional
    public AnimalPerdido cadastrarGatoPerdido(GatoPerdidoCreateDto gatoDto, MultipartFile fotoPerfil) {
        Optional<Ong> ongOpt = ongRepository.findById(gatoDto.getOngId());
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ONG não encontrada");
        }
        Ong ong = ongOpt.get();
        GatoPerdido gatoPerdido = new GatoPerdido(gatoDto);
        if(fotoPerfil != null && !fotoPerfil.isEmpty()) {
            try {
                Image image = cloudinaryService.upload(fotoPerfil);
                gatoPerdido.setFotoPerfil(image);
                Endereco endereco = viaCepService.obterEnderecoPorCep(gatoDto.getCep());
                enderecoRepository.save(endereco);
                gatoPerdido.setEnderecoPerdido(endereco);
                gatoPerdido.setOng(ong);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



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

    public List<AnimalPerdidoAchadoPerdidoDto> recuperarAnimaisPerdidosEAchados(Long ongId) {
        Optional<Ong> ongOpt = ongRepository.findById(ongId);
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }
        Ong ong = ongOpt.get();
        List<AnimalPerdido> animais = animalPerdidoRepository.findByOng(ong);
        List<AnimalPerdidoAchadoPerdidoDto> animaisDto = new ArrayList<>();
        for (AnimalPerdido animal : animais) {
            AnimalPerdidoAchadoPerdidoDto animalDto = AnimalPerdidoMapper.toAnimalPerdidoAchadoPerdidoDto(animal);
            animaisDto.add(animalDto);

        }
        return animaisDto;
    }

    public List<AnimalPerdidoCardDto> recuperarAnimaisPerdidosCard(Long ongId) {
        Optional<Ong> ongOpt = ongRepository.findById(ongId);
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }
        Ong ong = ongOpt.get();
        List<AnimalPerdido> animais = animalPerdidoRepository.findByOng(ong);
        List<AnimalPerdidoCardDto> animaisDto = new ArrayList<>();
        for (AnimalPerdido animal : animais) {
            AnimalPerdidoCardDto animalDto = AnimalPerdidoMapper.toAnimalPerdidoCardDto(animal);
            animaisDto.add(animalDto);

        }
        return animaisDto;
    }
}
