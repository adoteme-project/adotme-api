package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.ongUser.OngUserUpdateDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.ongUser.OngUser;
import com.example.adpotme_api.dto.ongUser.OngUserCreateDto;
import com.example.adpotme_api.service.OngUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "OngUser", description = "Controlador para operações relacionadas aos usuários de ONGs.")
@SecurityRequirement(name = "bearerAuth")
public class OngUserController {

    @Autowired
    private OngUserService ongUserService;

    @PostMapping
    @Transactional
    @Operation(summary = "Criar um novo usuário ONG", description = "Cadastra um novo usuário ONG no sistema com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário ONG cadastrado com sucesso.")
    })
    public ResponseEntity<OngUser> createOngUser(@RequestBody @Valid OngUserCreateDto dto) {
        OngUser ongUser = ongUserService.createOngUser(dto);
        return ResponseEntity.status(201).body(ongUser);
    }

    @GetMapping
    @Operation(summary = "Recuperar todos os usuários ONG", description = "Retorna uma lista de todos os usuários ONG cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários ONG retornada com sucesso.")
    })
    public ResponseEntity<List<OngUser>> recuperarOngUsers() {
        List<OngUser> ongUsers = ongUserService.findAllOngUsers();
        return ResponseEntity.ok(ongUsers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperar usuário ONG por ID", description = "Retorna um usuário ONG específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário ONG encontrado.")
    })
    public ResponseEntity<OngUser> recuperarOngUserPorId(@Parameter(description = "ID do usuário ONG a ser recuperado", required = true) @PathVariable Long id) {
        OngUser ongUser = ongUserService.findOngUserById(id);
        if (ongUser == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(ongUser);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualizar usuário ONG", description = "Atualiza os dados de um usuário ONG específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário ONG atualizado com sucesso.")
    })
    public ResponseEntity<OngUser> atualizar(@Parameter(description = "ID do usuário ONG a ser atualizado", required = true) @PathVariable Long id,
                                             @RequestBody OngUserUpdateDto ongUserAtualizada) {
        OngUser updatedOngUser = ongUserService.updateOngUser(id, ongUserAtualizada);
        return ResponseEntity.ok(updatedOngUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário ONG", description = "Deleta um usuário ONG específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário ONG deletado com sucesso.")
    })
    public ResponseEntity<Void> deletarOngUser(@Parameter(description = "ID do usuário ONG a ser deletado", required = true) @PathVariable Long id) {
        ongUserService.deleteOngUser(id);
        return ResponseEntity.status(204).build();
    }

    @PostMapping("/{idOngUser}/{idRequisicao}")
    @Operation(summary = "Iniciar revisão para um usuário ONG", description = "Inicia o processo de revisão para um usuário ONG com base nos IDs fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Revisão iniciada com sucesso.")
    })
    public ResponseEntity<Void> iniciarRevisao(@Parameter(description = "ID do usuário ONG", required = true) @PathVariable Long idOngUser,
                                               @Parameter(description = "ID da solicitação", required = true) @PathVariable Long idRequisicao) {
        ongUserService.iniciarRevisao(idOngUser, idRequisicao);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/adocao-animal/{id}/{idAnimal}")
    @Transactional
    @Operation(summary = "Adotar um animal", description = "Processa a adoção de um animal por um usuário ONG.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adoção realizada com sucesso.")
    })
    public ResponseEntity<Adotante> adotarAnimal(@Parameter(description = "ID do usuário ONG", required = true) @PathVariable Long id,
                                                 @Parameter(description = "ID do animal a ser adotado", required = true) @PathVariable Long idAnimal) {
        Adotante adotante = ongUserService.adotarAnimal(id, idAnimal);
        return ResponseEntity.ok(adotante);
    }
}
