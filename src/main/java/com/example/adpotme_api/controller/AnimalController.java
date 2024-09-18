package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.animal.AnimalUpdateDto;
import com.example.adpotme_api.dto.animal.CachorroCreateDto;
import com.example.adpotme_api.dto.animal.GatoCreateDto;
import com.example.adpotme_api.entity.animal.*;
import com.example.adpotme_api.service.AnimalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animais")
@Tag(name = "Animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping("/cachorro")
    public ResponseEntity<Animal> cadastrarCachorro(@RequestBody @Valid CachorroCreateDto cachorroDto) {
        Animal cachorro = animalService.cadastrarCachorro(cachorroDto);
        return ResponseEntity.status(201).body(cachorro);
    }

    @PostMapping("/gato")
    public ResponseEntity<Animal> cadastrarGato(@RequestBody @Valid GatoCreateDto gatoDto) {
        Animal gato = animalService.cadastrarGato(gatoDto);
        return ResponseEntity.status(201).body(gato);
    }

    @GetMapping
    public ResponseEntity<List<Animal>> recuperarAnimais() {
        List<Animal> animais = animalService.recuperarAnimais();
        if (animais.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(animais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> recuperarAnimalPorId(@PathVariable Long id) {
        Animal animal = animalService.recuperarAnimalPorId(id);
        return ResponseEntity.ok(animal);
    }

    @PutMapping("/cachorro/{id}")
    public ResponseEntity<Cachorro> atualizarCachorro(@PathVariable Long id, @RequestBody AnimalUpdateDto cachorroAtualizado) {
        Cachorro atualizado = (Cachorro) animalService.atualizarCachorro(id, cachorroAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    @PutMapping("/gato/{id}")
    public ResponseEntity<Gato> atualizarGato(@PathVariable Long id, @RequestBody AnimalUpdateDto gatoAtualizado) {
        Gato atualizado = (Gato) animalService.atualizarGato(id, gatoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAnimal(@PathVariable Long id) {
        animalService.deletarAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
