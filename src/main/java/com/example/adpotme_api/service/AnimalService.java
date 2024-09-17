package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.animal.CachorroCreateDto;
import com.example.adpotme_api.dto.animal.GatoCreateDto;
import com.example.adpotme_api.entity.animal.*;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.repository.AnimalRepository;
import com.example.adpotme_api.repository.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private OngRepository ongRepository;

    @Transactional
    public Animal cadastrarCachorro(CachorroCreateDto cachorroDto) {
        Optional<Ong> ongOpt = ongRepository.findById(cachorroDto.getOngId());
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }
        Ong ong = ongOpt.get();

        Cachorro cachorro = new Cachorro(cachorroDto);
        cachorro.setOng(ong);
        cachorro.calcularTaxaAdocao();
        return animalRepository.save(cachorro);
    }

    @Transactional
    public Animal cadastrarGato(GatoCreateDto gatoDto) {
        Optional<Ong> ongOpt = ongRepository.findById(gatoDto.getOngId());
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }
        Ong ong = ongOpt.get();

        Gato gato = new Gato(gatoDto);
        gato.setOng(ong);
        gato.calcularTaxaAdocao();
        return animalRepository.save(gato);
    }

    public List<Animal> recuperarAnimais() {
        return animalRepository.findAll();
    }

    public Animal recuperarAnimalPorId(Long id) {
        Optional<Animal> animalOpt = animalRepository.findById(id);
        if (animalOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }
        return animalOpt.get();
    }

    @Transactional
    public Animal atualizarCachorro(Long id, CachorroCreateDto cachorroAtualizado) {
        Optional<Animal> animalOpt = animalRepository.findById(id);
        if (animalOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cachorro não encontrado");
        }
        Cachorro cachorro = (Cachorro) animalOpt.get();

        if (!ongRepository.existsById(cachorroAtualizado.getOngId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }

        atualizarDadosCachorro(cachorro, cachorroAtualizado);

        return animalRepository.save(cachorro);
    }

    @Transactional
    public Animal atualizarGato(Long id, GatoCreateDto gatoAtualizado) {
        Optional<Animal> animalOpt = animalRepository.findById(id);
        if (animalOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gato não encontrado");
        }
        Gato gato = (Gato) animalOpt.get();

        if (!ongRepository.existsById(gatoAtualizado.getOngId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }

        atualizarDadosGato(gato, gatoAtualizado);

        return animalRepository.save(gato);
    }

    @Transactional
    public void deletarAnimal(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }
        animalRepository.deleteById(id);
    }

    private void atualizarDadosCachorro(Cachorro cachorro, CachorroCreateDto dto) {
        cachorro.setNome(dto.getNome());
        cachorro.setAnoNascimento(dto.getAnoNascimento());
        cachorro.setSexo(dto.getSexo());
        cachorro.setDataAbrigo(dto.getDataAbrigo());
        cachorro.setEspecie(dto.getEspecie());
        cachorro.setRaca(dto.getRaca());
        cachorro.setIsCastrado(dto.getIsCastrado());
        cachorro.setDescricao(dto.getDescricao());
        cachorro.setIsVisible(dto.getIsVisible());
        cachorro.setIsAdotado(dto.getIsAdotado());
        cachorro.setIsDestaque(dto.getIsDestaque());
        cachorro.setPorte(dto.getPorte());
        cachorro.setIsVermifugado(dto.getIsVermifugado());

        if (dto.getTaxaAdocao() != null) {
            cachorro.setTaxaAdocao(dto.getTaxaAdocao());
        } else {
            cachorro.calcularTaxaAdocao();
        }

        Optional<Ong> ongOpt = ongRepository.findById(dto.getOngId());
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }
        cachorro.setOng(ongOpt.get());
    }

    private void atualizarDadosGato(Gato gato, GatoCreateDto dto) {
        gato.setNome(dto.getNome());
        gato.setAnoNascimento(dto.getAnoNascimento());
        gato.setDataAbrigo(dto.getDataAbrigo());
        gato.setSexo(dto.getSexo());
        gato.setEspecie(dto.getEspecie());
        gato.setRaca(dto.getRaca());
        gato.setIsDestaque(dto.getIsDestaque());
        gato.setIsCastrado(dto.getIsCastrado());
        gato.setDescricao(dto.getDescricao());
        gato.setIsVisible(dto.getIsVisible());
        gato.setIsAdotado(dto.getIsAdotado());
        gato.setIsVermifugado(dto.getIsVermifugado());

        if (dto.getTaxaAdocao() != null) {
            gato.setTaxaAdocao(dto.getTaxaAdocao());
        } else {
            gato.calcularTaxaAdocao();
        }

        Optional<Ong> ongOpt = ongRepository.findById(dto.getOngId());
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }
        gato.setOng(ongOpt.get());
    }
}
