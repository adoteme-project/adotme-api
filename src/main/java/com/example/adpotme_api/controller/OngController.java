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

@CrossOrigin(origins = "*")
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
                                            MultipartFile qrCode,
                                                          @RequestPart(value ="imgOng", required = false) MultipartFile imgOng
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        OngCreateDto dados = objectMapper.readValue(ongJson, OngCreateDto.class);
        OngResponseAllDto ong = ongService.cadastrarOng(dados, numero, qrCode, imgOng);

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

    @GetMapping("/ong-edicao-visualizacao/{idOng}")
    @Operation(summary = "Recuperar ONG para edição/visualização", description = "Retorna uma ONG específica com base no ID fornecido para edição/visualização.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ONG encontrada."),
            @ApiResponse(responseCode = "404", description = "ONG não encontrada.")
    })
    public ResponseEntity<OngPutViewDto> recuperarOngParaEdicaoVisualizacao(@Parameter(description = "ID da ONG a ser recuperada", required = true) @PathVariable Long idOng) {
        OngPutViewDto ong = ongService.recuperarOngParaEdicaoVisualizacao(idOng);
        return ResponseEntity.ok(ong);
    }

    @PutMapping("/editar/{idOng}")
    @Transactional
    @Operation(summary = "Editar ONG", description = "Edita os dados de uma ONG específica com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ONG editada com sucesso."),
            @ApiResponse(responseCode = "404", description = "ONG não encontrada."),
            @ApiResponse(responseCode = "400", description = "Erro ao editar a ONG.")
    })
    public ResponseEntity<OngPutDto> editarOng(@Parameter(description = "ID da ONG a ser editada", required = true) @PathVariable Long idOng,
                                         @RequestBody @Valid OngPutDto ongAtualizada) {
        OngPutDto ongAtt = ongService.editarOng(idOng, ongAtualizada);
        return ResponseEntity.status(200).body(ongAtt);
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



    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualizar ONG", description = "Atualiza os dados de uma ONG específica com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ONG atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "ONG não encontrada."),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar a ONG.")
    })
    public ResponseEntity<Ong> atualizar(@Parameter(description = "ID da ONG a ser atualizada", required = true) @PathVariable Long id,
                                         @RequestBody @Valid OngUpdateDto ongAtualizada) {
        Ong updatedOng = ongService.atualizar(id, ongAtualizada);
        return ResponseEntity.status(200).body(updatedOng);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar ONG", description = "Deleta uma ONG específica com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "ONG deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "ONG não encontrada.")
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

    @GetMapping("/listagem-ongs-com-animais-dados-bancarios")
    @Operation(summary = "Listagem de ONGs com animais e dados bancários", description = "Retorna uma lista de ONGs com animais e dados bancários cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ONGs retornada com sucesso."),
            @ApiResponse(responseCode = "204", description = "Nenhuma ONG encontrada.")
    })
    public ResponseEntity<List<OngDadosBancariosAnimalDto>> listagemOngsComAnimaisDadosBancarios() {
        List<OngDadosBancariosAnimalDto> ongs = ongService.listagemOngsComAnimaisDadosBancarios();
        if (ongs.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(ongs);
        }
    }
    @GetMapping("/nome-e-imagem-ong/{idOng}")
    @Operation(summary = "Recuperar nome e imagem da ONG", description = "Retorna o nome e a imagem de uma ONG específica com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nome e imagem da ONG retornados com sucesso."),
            @ApiResponse(responseCode = "404", description = "ONG não encontrada.")
    })
    public ResponseEntity<OngNomeImagemDto> recuperarNomeImagemOng(@Parameter(description = "ID da ONG a ser recuperada", required = true) @PathVariable Long idOng) {
        OngNomeImagemDto ong = ongService.recuperarNomeImagemOng(idOng);
        return ResponseEntity.ok(ong);
    }
}
