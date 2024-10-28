package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.ong.*;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.service.OngService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("ongs")
@Tag(name = "Ong", description = "Controlador para operações relacionadas a ONGs.")
@SecurityRequirement(name = "bearerAuth")
public class OngController {

    @Autowired
    private OngService ongService;

    @PostMapping(value="/cadastrar", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Transactional
    @Operation(summary = "Cadastrar uma nova ONG", description = "Cadastra uma ONG no sistema com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ONG cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados fornecidos.")
    })
    public ResponseEntity<OngResponseAllDto> cadastrarOng(@RequestPart("ong") String ongJson,
                                            @RequestPart("numero") String numero,
                                            @RequestPart(value = "qrCode", required = false)
                                            MultipartFile qrCode
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        OngCreateDto dados = objectMapper.readValue(ongJson, OngCreateDto.class);
        OngResponseAllDto ong = ongService.cadastrarOng(dados, numero, qrCode);

        return ResponseEntity.status(201).body(ong);
    }

    @GetMapping("/com-dados-bancarios")
    @Operation(summary = "Recuperar todas as ONGs com dados bancários", description = "Retorna uma lista de todas as ONGs com dados bancários cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ONGs com dados bancários retornada com sucesso."),
            @ApiResponse(responseCode = "204", description = "Nenhuma ONG encontrada.")
    })
    public ResponseEntity<List<OngResponseAllDto>> recuperarOngsComDadosBancarios() {
        List<OngResponseAllDto> ongs = ongService.recuperarOngsComDadosBancarios();
        if (ongs.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(ongs);
        }
    }


    @GetMapping("/com-animal")
    @Operation(summary = "Recuperar todas as ONGs", description = "Retorna uma lista de todas as ONGs com animais cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ONGs com animais retornada com sucesso."),
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

    @GetMapping("/pesquisa-por-nome")
    @Operation(summary = "Pesquisar ONG por nome", description = "Retorna uma ONG que possue o nome fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ONG encontrada."),
            @ApiResponse(responseCode = "404", description = "ONG não encontrada.")
    })

    public ResponseEntity<OngResponseAllDto> pesquisarOngPorNome(@Parameter(description = "Nome da ONG a ser pesquisada", required = true) @RequestParam String nome) {
        OngResponseAllDto ong = ongService.pesquisarOngPorNome(nome);
        return ResponseEntity.ok(ong);
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

    @GetMapping("/listagem-animais-ong/{id}")
    @Operation(summary = "Listagem de animais por ONG", description = "Retorna uma lista de animais cadastrados por uma ONG específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de animais retornada com sucesso."),
            @ApiResponse(responseCode = "204", description = "Nenhum animal encontrado.")
    })
    public ResponseEntity<List<OngAnimaisDto>> listagemAnimaisOng(@Parameter(description = "ID da ONG", required = true) @PathVariable Long id) {
        List<OngAnimaisDto> animais = ongService.listagemAnimaisOng(id);
        if (animais.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(animais);
        }
    }
}
