package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.animal.AnimalUpdateDto;
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
    public Animal atualizarCachorro(Long id, AnimalUpdateDto cachorroAtualizado) {
        Optional<Animal> animalOpt = animalRepository.findById(id);
        if (animalOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cachorro não encontrado");
        }
        Cachorro cachorro = (Cachorro) animalOpt.get();


        cachorro.setEspecie(cachorroAtualizado.getEspecie());
        cachorro.setNome(cachorroAtualizado.getNome());
        cachorro.setAnoNascimento(cachorroAtualizado.getAnoNascimento());
        cachorro.setSexo(cachorroAtualizado.getSexo());
        cachorro.setDataAbrigo(cachorroAtualizado.getDataAbrigo());
        cachorro.setRaca(cachorroAtualizado.getRaca());
        cachorro.setIsCastrado(cachorroAtualizado.getIsCastrado());
        cachorro.setDescricao(cachorroAtualizado.getDescricao());
        cachorro.setIsVisible(cachorroAtualizado.getIsVisible());
        cachorro.setIsAdotado(cachorroAtualizado.getIsAdotado());
        cachorro.setPorte(cachorroAtualizado.getPorte());
        cachorro.setIsVermifugado(cachorroAtualizado.getIsVermifugado());
        cachorro.setTaxaAdocao(cachorroAtualizado.getTaxaAdocao());
        cachorro.setIsDestaque(cachorroAtualizado.getIsDestaque());

        return animalRepository.save(cachorro);
    }

    @Transactional
    public Animal atualizarGato(Long id, AnimalUpdateDto gatoAtualizado) {
        Optional<Animal> animalOpt = animalRepository.findById(id);
        if (animalOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gato não encontrado");
        }
        Gato gato = (Gato) animalOpt.get();
        gato.setEspecie(gatoAtualizado.getEspecie());
        gato.setNome(gatoAtualizado.getNome());
        gato.setAnoNascimento(gatoAtualizado.getAnoNascimento());
        gato.setSexo(gatoAtualizado.getSexo());
        gato.setDataAbrigo(gatoAtualizado.getDataAbrigo());
        gato.setRaca(gatoAtualizado.getRaca());
        gato.setIsCastrado(gatoAtualizado.getIsCastrado());
        gato.setDescricao(gatoAtualizado.getDescricao());
        gato.setIsVisible(gatoAtualizado.getIsVisible());
        gato.setIsAdotado(gatoAtualizado.getIsAdotado());
        gato.setPorte(gatoAtualizado.getPorte());
        gato.setIsVermifugado(gatoAtualizado.getIsVermifugado());
        gato.setTaxaAdocao(gatoAtualizado.getTaxaAdocao());
        gato.setIsDestaque(gatoAtualizado.getIsDestaque());


        return animalRepository.save(gato);
    }

    @Transactional
    public void deletarAnimal(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }
        animalRepository.deleteById(id);
    }




}
