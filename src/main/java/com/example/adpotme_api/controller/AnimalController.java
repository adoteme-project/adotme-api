package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.animal.*;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.animal.Cachorro;
import com.example.adpotme_api.entity.animal.Gato;
import com.example.adpotme_api.service.AnimalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/animais")
@Tag(name = "Animal", description = "Controlador para operações relacionadas aos animais das ongs.")
@SecurityRequirement(name = "bearerAuth")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(value = "/cachorro", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Cadastra um cachorro", description = "Cadastra um novo cachorro no sistema com os dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "O cachorro foi cadastrado com sucesso no sistema.")
    @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o cachorro.")
    public ResponseEntity<Animal> cadastrarCachorro(
            @RequestPart("cachorro") String cachorroJson,
            @RequestPart(value = "Foto de Perfil Principal", required = false) MultipartFile fotoPerfil1,
            @RequestPart(value = "fotoPerfil2", required = false) MultipartFile fotoPerfil2,
            @RequestPart(value = "fotoPerfil3", required = false) MultipartFile fotoPerfil3,
            @RequestPart(value = "fotoPerfil4", required = false) MultipartFile fotoPerfil4,
            @RequestPart(value = "fotoPerfil5", required = false) MultipartFile fotoPerfil5
    ) throws JsonProcessingException {

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        CachorroCreateDto dados = objectMapper.readValue(cachorroJson, CachorroCreateDto.class);

        Animal cachorro = animalService.cadastrarCachorro(dados, fotoPerfil1, fotoPerfil2, fotoPerfil3, fotoPerfil4, fotoPerfil5);
        return ResponseEntity.status(201).body(cachorro);
    }

    @PostMapping(value="/gato", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Cadastra um gato", description = "Cadastra um novo gato no sistema com os dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "O gato foi cadastrado com sucesso no sistema.")
    @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o gato.")
    public ResponseEntity<Animal> cadastrarGato(
            @RequestPart("gato") String gatoJson,
            @RequestPart(value = "Foto de Perfil Principal", required = false) MultipartFile fotoPerfil1,
            @RequestPart(value = "fotoPerfil2", required = false) MultipartFile fotoPerfil2,
            @RequestPart(value = "fotoPerfil3", required = false) MultipartFile fotoPerfil3,
            @RequestPart(value = "fotoPerfil4", required = false) MultipartFile fotoPerfil4,
            @RequestPart(value = "fotoPerfil5", required = false) MultipartFile fotoPerfil5
    ) throws JsonProcessingException {

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        GatoCreateDto dados = objectMapper.readValue(gatoJson, GatoCreateDto.class);

        Animal gato = animalService.cadastrarGato(dados, fotoPerfil1, fotoPerfil2, fotoPerfil3, fotoPerfil4, fotoPerfil5);
        return ResponseEntity.status(201).body(gato);
    }



    @PutMapping("/cachorro/{id}")
    @Operation(summary = "Atualiza os dados de um cachorro", description = "Atualiza as informações de um cachorro existente com base no ID.")
    @ApiResponse(responseCode = "200", description = "Os dados do cachorro foram atualizados com sucesso.")
    @ApiResponse(responseCode = "404", description = "Cachorro não encontrado.")
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar o cachorro.")
    public ResponseEntity<Cachorro> atualizarCachorro(@PathVariable Long id, @RequestBody @Valid AnimalUpdateDto cachorroAtualizado) {
        Cachorro atualizado = (Cachorro) animalService.atualizarCachorro(id, cachorroAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    @PutMapping("/gato/{id}")
    @Operation(summary = "Atualiza os dados de um gato", description = "Atualiza as informações de um gato existente com base no ID.")
    @ApiResponse(responseCode = "200", description = "Os dados do gato foram atualizados com sucesso.")
    @ApiResponse(responseCode = "404", description = "Gato não encontrado.")
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar o gato.")
    public ResponseEntity<Gato> atualizarGato(@PathVariable Long id, @RequestBody @Valid AnimalUpdateDto gatoAtualizado) {
        Gato atualizado = (Gato) animalService.atualizarGato(id, gatoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um animal", description = "Remove um animal específico do sistema com base no ID fornecido.")
    @ApiResponse(responseCode = "204", description = "O animal foi removido do sistema com sucesso.")
    public ResponseEntity<Void> deletarAnimal(@PathVariable Long id) {
        animalService.deletarAnimal(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping(value = "/importar", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Importa dados de animais a partir de um arquivo CSV", description = "Importa dados de animais no sistema a partir de um arquivo CSV fornecido.")
    @ApiResponse(responseCode = "200", description = "Os dados foram importados com sucesso.")
    public ResponseEntity<Void> importarAnimais(@RequestPart("file") MultipartFile file) {
        animalService.importarAnimais(file);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/exportar/{id}", produces = "text/csv")
    @Operation(summary = "Exporta dados de animais para um arquivo CSV", description = "Exporta dados de todos os animais cadastrados no sistema para um arquivo CSV.")
    @ApiResponse(responseCode = "200", description = "Os dados foram exportados com sucesso.")
    @ApiResponse(responseCode = "404", description = "Ong não encontrada.")
    public void exportarAnimais(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=animais.csv");
        animalService.exportarAnimais(response.getWriter(), id);
    }
    @GetMapping("/todos-animais-com-personalidade/")
    @Operation(summary = "Retorna todos os animais com personalidade", description = "Recupera uma lista de todos os animais cadastrados no sistema com personalidade.")
    @ApiResponse(responseCode = "200", description = "A lista de animais foi recuperada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Não há animais cadastrados no sistema.")
    public ResponseEntity<List<AnimalOngResponseDto>> recuperarAnimaisComPersonalidade() {
        List<AnimalOngResponseDto> animais = animalService.recuperarAnimaisComPersonalidade();
        if (animais.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(animais);
    }
    @GetMapping("/animal-com-personalidade/{idAnimal}")
    @Operation(summary = "Retorna um animais com personalidade", description = "Recupera um animal com personalidade.")
    @ApiResponse(responseCode = "200", description = "O animal foi recuperado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Animal não encontrado.")
    public ResponseEntity<AnimalOngResponseDto> recuperarAnimalComPersonalidade(@PathVariable Long idAnimal) {
        AnimalOngResponseDto animal = animalService.recuperarAnimalComPersonalidade(idAnimal);
        return ResponseEntity.ok(animal);
    }



    @GetMapping("/todos-animais-pela-aplicacao-por-ong/{ongId}")
    @Operation(summary = "Retorna todos os animais pela aplicacao por ong", description = "Recupera uma lista de todos os animais cadastrados no sistema pela aplicacao por ong.")
    @ApiResponse(responseCode = "200", description = "A lista de animais foi recuperada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Não há animais cadastrados no sistema.")
    public ResponseEntity<List<AnimalAplicacaoDto>> recuperarAnimaisPelaAplicacaoPorOng(@PathVariable Long ongId) {
        List<AnimalAplicacaoDto> animais = animalService.recuperarAnimaisPelaAplicacaoPorOng(ongId);
        if (animais.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(animais);
    }

    @GetMapping("/animal-com-lista-aplicacao/{idAnimal}")
    @Operation(summary = "Retorna um animal com lista de aplicacao", description = "Recupera um animal com lista de aplicacao.")
    @ApiResponse(responseCode = "200", description = "O animal foi encontrado e sua lista de aplicacao foi retornada.")
    @ApiResponse(responseCode = "404", description = "Animal não encontrado.")
    public ResponseEntity<AnimalListaAplicacaoDto> recuperarAnimalComListaAplicacao(@PathVariable Long idAnimal) {
            AnimalListaAplicacaoDto animal = animalService.recuperarAnimalComListaAplicacao(idAnimal);
        return ResponseEntity.ok(animal);
    }
}
