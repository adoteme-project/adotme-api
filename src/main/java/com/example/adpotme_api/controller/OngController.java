package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.ong.OngUpdateDto;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.dto.ong.OngCreateDto;
import com.example.adpotme_api.service.OngService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("ongs")
@Tag(name = "Ong")
public class OngController {

    @Autowired
    private OngService ongService;

    @PostMapping
    @Transactional
    public ResponseEntity<Ong> cadastrarOng(@RequestBody @Valid OngCreateDto dados) {
        Ong ong = ongService.cadastrarOng(dados);
        return ResponseEntity.status(201).body(ong);
    }

    @GetMapping
    public ResponseEntity<List<Ong>> recuperarOngs() {
        List<Ong> ongs = ongService.recuperarOngs();
        if(ongs.isEmpty()) {
           return ResponseEntity.status(204).build();
        } else{
           return ResponseEntity.status(200).body(ongs);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ong> recuperarOngPorId(@PathVariable Long id) {
        Ong ong = ongService.recuperarOngPorId(id);
        return ResponseEntity.status(200).body(ong);
    }

    @GetMapping("/ordenados-por-estado")
    public ResponseEntity<List<Ong>> recuperarOngsOrdenadoPorEstado() {
        List<Ong> ongs = ongService.recuperarOngsOrdenadoPorEstado();
        return ResponseEntity.ok(ongs);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Ong> atualizar(@PathVariable Long id,
                                         @RequestBody OngUpdateDto ongAtualizada) {
        Ong updatedOng = ongService.atualizar(id, ongAtualizada);
        return ResponseEntity.status(200).body(updatedOng);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOng(@PathVariable Long id) {
        ongService.deletarOng(id);
        return ResponseEntity.status(204).build();
    }
}
