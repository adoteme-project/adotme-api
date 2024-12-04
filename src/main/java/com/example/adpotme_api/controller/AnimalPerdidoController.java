package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.animal.CachorroCreateDto;
import com.example.adpotme_api.dto.animalPerdido.*;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.animalPerdido.*;
import com.example.adpotme_api.mapper.AnimalPerdidoMapper;
import com.example.adpotme_api.service.AnimalPerdidoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/animais-perdidos")
@Tag(name = "AnimalPerdido", description = "Controlador para gestão de animais perdidos")
@SecurityRequirement(name = "bearerAuth")
public class AnimalPerdidoController {

    @Autowired
    private AnimalPerdidoService animalPerdidoService;
    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(value="/cachorro", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Cadastrar cachorro perdido", description = "Cadastra um novo cachorro perdido no sistema com os dados fornecidos no corpo da requisição.")
    @ApiResponse(responseCode = "201", description = "Cachorro perdido cadastrado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o cachorro perdido.")
    @ApiResponse(responseCode = "404", description = "Ong não encontrada.")
    public ResponseEntity<AnimalPerdido> cadastrarCachorroPerdido(
            @RequestPart("cachorro") String cachorroPerdidoJson,
            @RequestPart(value = "fotoPerfil", required = false) MultipartFile fotoPerfil
    ) throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        CachorroPerdidoCreateDto cachorroDto = objectMapper.readValue(cachorroPerdidoJson, CachorroPerdidoCreateDto.class);

        AnimalPerdido cachorro = animalPerdidoService.cadastrarCachorroPerdido(cachorroDto, fotoPerfil);
        return ResponseEntity.status(201).body(cachorro);
    }

    @PostMapping(value="/gato", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Cadastrar gato perdido", description = "Cadastra um novo gato perdido no sistema com os dados fornecidos no corpo da requisição.")
    @ApiResponse(responseCode = "201", description = "Gato perdido cadastrado com sucesso.")
    @ApiResponse(responseCode="400", description = "Erro ao cadastrar o gato perdido.")
    @ApiResponse(responseCode="404", description = "Ong não encontrada.")
    public ResponseEntity<AnimalPerdido> cadastrarGatoPerdido(
            @RequestPart("gato") String gatoPerdidoJson,
            @RequestPart(value = "fotoPerfil", required = false) MultipartFile fotoPerfil
    ) throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        GatoPerdidoCreateDto gatoDto = objectMapper.readValue(gatoPerdidoJson, GatoPerdidoCreateDto.class);

        AnimalPerdido cachorro = animalPerdidoService.cadastrarGatoPerdido(gatoDto, fotoPerfil);
        return ResponseEntity.status(201).body(cachorro);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar animal perdido", description = "Deleta um animal perdido do sistema com base no ID fornecido. Se o animal não for encontrado, um código 404 será retornado.")
    @ApiResponse(responseCode = "204", description = "Animal perdido deletado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Animal perdido não encontrado.")
    public ResponseEntity<Void> deletarAnimal(
            @PathVariable Long id) {

        animalPerdidoService.deletarAnimal(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/animal-perdido-achados-perdidos/")
    @Operation(summary = "Recuperar animais perdidos e achados", description = "Retorna uma lista de animais perdidos e achados cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de animais perdidos e achados recuperada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Nenhum animal perdido ou achado encontrado.")
    public ResponseEntity <List<AnimalPerdidoAchadoPerdidoDto>> recuperarAnimaisPerdidosEAchados(
          ) {

        List<AnimalPerdidoAchadoPerdidoDto> animais = animalPerdidoService.recuperarAnimaisPerdidosEAchados();
        if(animais.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(animais);
    }

    @GetMapping("/animal-perdido-achados-perdidos-por-ong/{idOng}")
    @Operation(summary = "Recuperar animais perdidos e achados", description = "Retorna uma lista de animais perdidos e achados cadastrados no sistema com base no ID da ONG fornecido.")
    @ApiResponse(responseCode = "200", description = "Lista de animais perdidos e achados recuperada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Nenhum animal perdido ou achado encontrado.")
    public ResponseEntity <List<AnimalPerdidoAchadoPerdidoDto>> recuperarAnimaisPerdidosEAchadosPorOng(
            @PathVariable Long idOng) {

        List<AnimalPerdidoAchadoPerdidoDto> animais = animalPerdidoService.recuperarAnimaisPerdidosPorOng(idOng);
        if(animais.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(animais);
    }

    @GetMapping("/animal-perdido-achados-perdidos-por-ong-tabela/{idOng}")
    @Operation(summary = "Recuperar animais perdidos e achados", description = "Retorna uma lista de animais perdidos com os dados resumidos de acordo com o ID da ONG fornecido.")
    @ApiResponse(responseCode = "200", description = "Lista de animais perdidos e achados recuperada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Nenhum animal perdido ou achado encontrado.")
    public ResponseEntity <List<AnimalPerdidoAchadoPerdidoTabelaDto>> recuperarAnimaisPerdidosResumidoPorOng(
            @PathVariable Long idOng) {

        List<AnimalPerdidoAchadoPerdidoTabelaDto> animais = animalPerdidoService.recuperarAnimaisPerdidosPorOngTabela(idOng);
        if(animais.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(animais);
    }

    @GetMapping("/animal-perdido-achados-perdidos-card/{idAnimalPerdido}")
    @Operation(summary = "Recuperar animais perdidos e achados", description = "Retorna uma lista de animais perdidos e achados cadastrados no sistema com base no Id do animal.")
    @ApiResponse(responseCode = "200", description = "Animal perdido recuperado com sucesso.")
    @ApiResponse(responseCode = "204", description = "Nenhum animal perdido ou achado encontrado.")
    public ResponseEntity <AnimalPerdidoCardDto> recuperarAnimalPerdidoCardPorId(
            @PathVariable Long idAnimalPerdido) {

        AnimalPerdidoCardDto animais = animalPerdidoService.recuperarAnimalPerdidoCardPorId(idAnimalPerdido);


        return ResponseEntity.status(200).body(animais);
    }


}
