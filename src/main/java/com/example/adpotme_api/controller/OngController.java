package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.ong.OngResponseDto;
import com.example.adpotme_api.dto.ong.OngUpdateDto;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.dto.ong.OngCreateDto;
import com.example.adpotme_api.service.OngService;
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
@RequestMapping("ongs")
@Tag(name = "Ong", description = "Controlador para operações relacionadas a ONGs.")
@SecurityRequirement(name = "bearerAuth")
public class OngController {

    @Autowired
    private OngService ongService;

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar uma nova ONG", description = "Cadastra uma ONG no sistema com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ONG cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados fornecidos.")
    })
    public ResponseEntity<Ong> cadastrarOng(@RequestBody @Valid OngCreateDto dados, @RequestParam String numero) {
        Ong ong = ongService.cadastrarOng(dados, numero);
        return ResponseEntity.status(201).body(ong);
    }

    @GetMapping
    @Operation(summary = "Recuperar todas as ONGs", description = "Retorna uma lista de todas as ONGs cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ONGs retornada com sucesso."),
            @ApiResponse(responseCode = "204", description = "Nenhuma ONG encontrada.")
    })
    public ResponseEntity<List<OngResponseDto>> recuperarOngs() {
        List<OngResponseDto> ongs = ongService.recuperarOngs();
        if (ongs.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(ongs);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperar ONG por ID", description = "Retorna uma ONG específica com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ONG encontrada."),
    })
    public ResponseEntity<Ong> recuperarOngPorId(@Parameter(description = "ID da ONG a ser recuperada", required = true) @PathVariable Long id) {
        Ong ong = ongService.recuperarOngPorId(id);
        if (ong == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(ong);
    }

    @GetMapping("/ordenados-por-estado")
    @Operation(summary = "Recuperar ONGs ordenadas por estado", description = "Retorna uma lista de ONGs ordenadas por estado.")
    @ApiResponse(responseCode = "200", description = "Lista de ONGs ordenada retornada com sucesso.")
    public ResponseEntity<List<Ong>> recuperarOngsOrdenadoPorEstado() {
        List<Ong> ongs = ongService.recuperarOngsOrdenadoPorEstado();
        return ResponseEntity.ok(ongs);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualizar ONG", description = "Atualiza os dados de uma ONG específica com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ONG atualizada com sucesso."),
    })
    public ResponseEntity<Ong> atualizar(@Parameter(description = "ID da ONG a ser atualizada", required = true) @PathVariable Long id,
                                         @RequestBody OngUpdateDto ongAtualizada) {
        Ong updatedOng = ongService.atualizar(id, ongAtualizada);
        return ResponseEntity.status(200).body(updatedOng);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar ONG", description = "Deleta uma ONG específica com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "ONG deletada com sucesso."),
    })
    public ResponseEntity<Void> deletarOng(@Parameter(description = "ID da ONG a ser deletada", required = true) @PathVariable Long id) {
        ongService.deletarOng(id);
        return ResponseEntity.status(204).build();
    }
}
