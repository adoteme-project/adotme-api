package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.animalPerdido.AnimalPerdidoUpdateDto;
import com.example.adpotme_api.dto.animalPerdido.CachorroPerdidoCreateDto;
import com.example.adpotme_api.dto.animalPerdido.GatoPerdidoCreateDto;
import com.example.adpotme_api.entity.animalPerdido.*;
import com.example.adpotme_api.service.AnimalPerdidoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/animais-perdidos")
@Tag(name = "AnimalPerdido")
@SecurityRequirement(name = "bearerAuth")
public class AnimalPerdidoController {

    @Autowired
    private AnimalPerdidoService animalPerdidoService;

    @PostMapping("/cachorro")
    public ResponseEntity<AnimalPerdido> cadastrarCachorroPerdido(@RequestBody @Valid CachorroPerdidoCreateDto cachorroDto) {
        AnimalPerdido cachorro = animalPerdidoService.cadastrarCachorroPerdido(cachorroDto);
        return ResponseEntity.status(201).body(cachorro);
    }

    @PostMapping("/gato")
    public ResponseEntity<AnimalPerdido> cadastrarGatoPerdido(@RequestBody @Valid GatoPerdidoCreateDto gatoDto) {
        AnimalPerdido gato = animalPerdidoService.cadastrarGatoPerdido(gatoDto);
        return ResponseEntity.status(201).body(gato);
    }

    @GetMapping
    public ResponseEntity<List<AnimalPerdido>> recuperarAnimais() {
        List<AnimalPerdido> animais = animalPerdidoService.recuperarAnimais();
        if (animais.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(animais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalPerdido> recuperarAnimalPorId(@PathVariable Long id) {
        AnimalPerdido animal = animalPerdidoService.recuperarAnimalPorId(id);
        return ResponseEntity.status(200).body(animal);
    }

    @GetMapping("/ordenados-por-estado")
    public ResponseEntity<List<AnimalPerdido>> recuperarAnimaisOrdenadosPorEstado() {
        List<AnimalPerdido> animais = animalPerdidoService.recuperarAnimaisOrdenadosPorEstado();
        return ResponseEntity.status(200).body(animais);
    }

    @PutMapping("/cachorro/{id}")
    public ResponseEntity<CachorroPerdido> atualizarCachorro(@PathVariable Long id, @RequestBody AnimalPerdidoUpdateDto cachorroDto) {
        CachorroPerdido cachorro = animalPerdidoService.atualizarCachorroPerdido(id, cachorroDto);
        return ResponseEntity.status(200).body(cachorro);
    }

    @PutMapping("/gato/{id}")
    public ResponseEntity<GatoPerdido> atualizarGato(@PathVariable Long id, @RequestBody AnimalPerdidoUpdateDto gatoDto) {
        GatoPerdido gato = animalPerdidoService.atualizarGatoPerdido(id, gatoDto);
        return ResponseEntity.status(200).body(gato);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAnimal(@PathVariable Long id) {
        animalPerdidoService.deletarAnimal(id);
        return ResponseEntity.status(204).build();
    }
}
