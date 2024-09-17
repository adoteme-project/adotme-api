package com.example.adpotme_api.controller;

import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.dto.adotante.AdotanteCreateDto;
import com.example.adpotme_api.service.AdotanteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adotantes")
@Tag(name = "Adotante")
public class AdotanteController {

    @Autowired
    private AdotanteService adotanteService;

    @PostMapping
    public ResponseEntity<Adotante> cadastrarAdotante(@RequestBody @Valid AdotanteCreateDto dados) {
        Adotante adotante = adotanteService.cadastrarAdotante(dados);
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
    public ResponseEntity<Adotante> atualizarAdotante(@PathVariable Long id, @RequestBody AdotanteCreateDto adotante) {
        Adotante atualizado = adotanteService.atualizarAdotante(id, adotante);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAdotante(@PathVariable Long id) {
        adotanteService.deletarAdotante(id);
        return ResponseEntity.status(204).build();
    }
}
