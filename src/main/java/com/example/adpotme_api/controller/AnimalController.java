package com.example.adpotme_api.controller;

import com.example.adpotme_api.animal.*;
import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.ong.OngRepository;
import com.example.adpotme_api.ongUser.OngUser;
import com.example.adpotme_api.ongUser.OngUserCreateDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/animais")
@Tag(name = "Animal")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private OngRepository ongRepository;

    @PostMapping("/cachorro")
    @Transactional
    public ResponseEntity<?> cadastrarCachorro(@RequestBody @Valid CachorroCreateDto cachorroDto) {
        Ong ong = ongRepository.findById(cachorroDto.getOngId())
                .orElseThrow(() -> new RuntimeException("ONG não encontrada"));



                Animal animal;


                Cachorro cachorro = new Cachorro(cachorroDto);
                cachorro.setOng(ong);
                cachorro.calcularTaxaAdocao();
                animal = cachorro;
        animalRepository.save(animal);
        return ResponseEntity.ok("Animal cadastrado com sucesso.");
    }
    @PostMapping("/gato")
    @Transactional
    public ResponseEntity<?> cadastrarGato(@RequestBody @Valid GatoCreateDto gatoDto) {
        Ong ong = ongRepository.findById(gatoDto.getOngId())
                .orElseThrow(() -> new RuntimeException("ONG não encontrada"));
        Animal animal;
        Gato gato = new Gato(gatoDto);
        gato.setOng(ong);
        gato.calcularTaxaAdocao();
        animal = gato;
        animalRepository.save(animal);
        return ResponseEntity.ok("Animal cadastrado com sucesso.");
    }
    @GetMapping
    public List<Animal> recuperarAnimais(){
        return animalRepository.findAll();
    }

    @GetMapping("/{id}")
    public Animal recuperarAnimalPorId(@PathVariable Long id) {
        Optional<Animal> animal = animalRepository.findById(id);

        if(animal.isPresent()) {
            return animal.get();
        }

        return null;
    }

    @PutMapping("cachorro/{id}")
    @Transactional
    public Cachorro atualizarCachorro(@PathVariable Long id, @RequestBody CachorroCreateDto cachorroAtualizado) {

        Cachorro cachorro = (Cachorro) animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found"));


        cachorro.setNome(cachorroAtualizado.getNome());
        cachorro.setAnoNascimento(cachorroAtualizado.getAnoNascimento());
        cachorro.setSexo(cachorroAtualizado.getSexo());
        cachorro.setEspecie(cachorroAtualizado.getEspecie());
        cachorro.setIsCastrado(cachorroAtualizado.getIsCastrado());
        cachorro.setDescricao(cachorroAtualizado.getDescricao());
        cachorro.setIsVisible(cachorroAtualizado.getIsVisible());
        cachorro.setIsAdotado(cachorroAtualizado.getIsAdotado());
        cachorro.setPorte(cachorroAtualizado.getPorte());
        cachorro.setIsVermifugado(cachorroAtualizado.getIsVermifugado());
        cachorro.setTaxaAdocao(cachorroAtualizado.getTaxaAdocao());


        // aqui to vendo se o id tá ong é valido e atualizando
        if (cachorroAtualizado.getOngId() != null) {
            Ong ong = ongRepository.findById(cachorroAtualizado.getOngId())
                    .orElseThrow(() -> new RuntimeException("Ong not found"));
            cachorro.setOng(ong);
        }


        return animalRepository.save(cachorro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAnimal(@PathVariable Long id) {
        if (animalRepository.existsById(id)) {
            animalRepository.deleteById(id);
            return ResponseEntity.status(204).build();

        }
        return ResponseEntity.status(404).build();
    }


}
