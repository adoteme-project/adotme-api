package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.adotante.AdotanteCreateDto;
import com.example.adpotme_api.dto.animal.AnimalUpdateDto;
import com.example.adpotme_api.dto.animal.CachorroCreateDto;
import com.example.adpotme_api.dto.animal.GatoCreateDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.*;
import com.example.adpotme_api.service.AnimalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/animais")
@Tag(name = "Animal")
@SecurityRequirement(name = "bearerAuth")
public class AnimalController {

    @Autowired
    private AnimalService animalService;
    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(value = "/cachorro", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Animal> cadastrarCachorro(
            @RequestPart("cachorro") String cachorroJson,
            @RequestPart(value = "fotoPerfil", required = false) MultipartFile fotoPerfil
    ) throws JsonProcessingException {

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  // Desabilitar timestamps para LocalDate

        // Desserializar JSON para DTO
        CachorroCreateDto dados = objectMapper.readValue(cachorroJson, CachorroCreateDto.class);

        Animal cachorro = animalService.cadastrarCachorro(dados, fotoPerfil);
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
