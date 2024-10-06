package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.adotante.AdotanteUpdateDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.dto.adotante.AdotanteCreateDto;
import com.example.adpotme_api.service.AdotanteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/adotantes")
@Tag(name = "Adotante")
@SecurityRequirement(name = "bearerAuth")
public class AdotanteController {

    @Autowired
    private AdotanteService adotanteService;

    @PostMapping(value = "/cadastrar", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Adotante> cadastrarAdotante(
            @RequestPart("adotante") String adotanteJson,
            @RequestPart(value = "fotoPerfil", required = false) MultipartFile fotoPerfil
    ) throws JsonProcessingException {

        // Criar ObjectMapper e registrar o módulo JavaTimeModule
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  // Desabilitar timestamps para LocalDate

        // Desserializar JSON para DTO
        AdotanteCreateDto dados = objectMapper.readValue(adotanteJson, AdotanteCreateDto.class);

        Adotante adotante = adotanteService.cadastrarAdotante(dados, fotoPerfil);
        return ResponseEntity.status(201).body(adotante);
    }


    @GetMapping
    public ResponseEntity<List<Adotante>> recuperarAdotantes() {
        List<Adotante> adotantes = adotanteService.recuperarAdotantes();
        if (adotantes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(adotantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Adotante> recuperarAdotantePorId(@PathVariable Long id) {
        Adotante adotante = adotanteService.recuperarAdotantePorId(id);
        return ResponseEntity.ok(adotante);
    }

    @GetMapping("/ordenados-por-estado")
    public ResponseEntity<List<Adotante>> recuperarAdotantesOrdenadosPorEstado() {
        List<Adotante> adotantes = adotanteService.recuperarAdotantesOrdenadosPorEstado();
        return ResponseEntity.ok(adotantes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Adotante> atualizarAdotante(@PathVariable Long id, @RequestBody AdotanteUpdateDto adotante) {
        Adotante atualizado = adotanteService.atualizarAdotante(id, adotante);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAdotante(@PathVariable Long id) {
        adotanteService.deletarAdotante(id);
        return ResponseEntity.status(204).build();
    }
}
