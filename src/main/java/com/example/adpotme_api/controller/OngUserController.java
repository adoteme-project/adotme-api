package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.ongUser.OngUserUpdateDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.ongUser.OngUser;
import com.example.adpotme_api.dto.ongUser.OngUserCreateDto;
import com.example.adpotme_api.service.OngUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ongusers")
@Tag(name = "OngUser")
@SecurityRequirement(name = "bearerAuth")
public class OngUserController {

    @Autowired
    private OngUserService ongUserService;

    @PostMapping
    @Transactional
    public ResponseEntity<OngUser> createOngUser(@RequestBody @Valid OngUserCreateDto dto) {
        OngUser ongUser = ongUserService.createOngUser(dto);
        return ResponseEntity.status(201).body(ongUser);
    }

    @GetMapping
    public ResponseEntity<List<OngUser>> recuperarOngUsers() {
        List<OngUser> ongUsers = ongUserService.findAllOngUsers();
        if (!ongUsers.isEmpty()) {
            return ResponseEntity.ok(ongUsers);
        }
        return ResponseEntity.ok(ongUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OngUser> recuperarOngUserPorId(@PathVariable Long id) {
        OngUser ongUser = ongUserService.findOngUserById(id);
        return ResponseEntity.ok(ongUser);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<OngUser> atualizar(@PathVariable Long id, @RequestBody OngUserUpdateDto ongUserAtualizada) {
        OngUser updatedOngUser = ongUserService.updateOngUser(id, ongUserAtualizada);
        return ResponseEntity.ok(updatedOngUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOngUser(@PathVariable Long id) {
        ongUserService.deleteOngUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idOngUser}/{idRequisicao}")
    public ResponseEntity<Void> iniciarRevisao(@PathVariable Long idOngUser, @PathVariable Long idRequisicao) {
        ongUserService.iniciarRevisao(idOngUser, idRequisicao);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/adocao-animal/{id}/{idAnimal}")
    @Transactional
    public ResponseEntity<Adotante> adotarAnimal(@PathVariable Long id, @PathVariable Long idAnimal) {
        Adotante adotante = ongUserService.adotarAnimal(id, idAnimal);
        return ResponseEntity.ok(adotante);
    }
}
