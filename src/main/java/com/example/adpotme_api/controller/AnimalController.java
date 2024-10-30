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
            @RequestPart(value = "fotoPerfil", required = false) MultipartFile fotoPerfil
    ) throws JsonProcessingException {

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        CachorroCreateDto dados = objectMapper.readValue(cachorroJson, CachorroCreateDto.class);

        Animal cachorro = animalService.cadastrarCachorro(dados, fotoPerfil);
        return ResponseEntity.status(201).body(cachorro);
    }

    @PostMapping(value="/gato", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Cadastra um gato", description = "Cadastra um novo gato no sistema com os dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "O gato foi cadastrado com sucesso no sistema.")
    @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o gato.")
    public ResponseEntity<Animal> cadastrarGato(
            @RequestPart("gato") String gatoJson,
            @RequestPart(value = "fotoPerfil", required = false) MultipartFile fotoPerfil
    ) throws JsonProcessingException {

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        GatoCreateDto dados = objectMapper.readValue(gatoJson, GatoCreateDto.class);

        Animal gato = animalService.cadastrarGato(dados, fotoPerfil);
        return ResponseEntity.status(201).body(gato);
    }
    @GetMapping("/por-personalidade")
    @Operation(summary = "Retorna animais por personalidade", description = "Recupera uma lista de animais com base na personalidade fornecida.")
    @ApiResponse(responseCode = "200", description = "A lista de animais foi recuperada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Não há animais com a personalidade fornecida.")
    public ResponseEntity<Animal[]> recuperarAnimaisPorPersonalidade(@RequestParam String personalidade) {
        Animal[] animais = animalService.recuperarAnimaisPorPersonalidade(personalidade);
        return ResponseEntity.ok(animais);
    }

    @GetMapping
    @Operation(summary = "Retorna todos os animais", description = "Recupera uma lista de todos os animais cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "A lista de animais foi recuperada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Não há animais cadastrados no sistema.")
    public ResponseEntity<List<Animal>> recuperarAnimais() {
        List<Animal> animais = animalService.recuperarAnimais();
        if (animais.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(animais);
    }


    @GetMapping("/ong/quantidade/{ongId}")
    @Operation(summary = "Retorna a quantidade de animais por ong", description = "Recupera a quantidade de animais por ong.")
    @ApiResponse(responseCode = "200", description = "A quantidade de animais foi recuperada com sucesso.")
    @ApiResponse(responseCode = "404", description = "ONG não encontrada.")
    public ResponseEntity<AnimalQuantidadeDto> recuperarQuantidadeAnimaisOng(@PathVariable Long ongId) {
        Integer quantidadeAnimaisPorOng = animalService.recuperarQuantidadeAnimaisPorOng(ongId);

        AnimalQuantidadeDto animais = new AnimalQuantidadeDto();

        animais.setQuantidade(quantidadeAnimaisPorOng);
        return ResponseEntity.ok(animais);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um animal pelo ID", description = "Recupera os detalhes de um animal específico com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "O animal foi encontrado e seus detalhes foram retornados.")
    @ApiResponse(responseCode = "404", description = "Animal não encontrado.")
    public ResponseEntity<Animal> recuperarAnimalPorId(@PathVariable Long id) {
        Animal animal = animalService.recuperarAnimalPorId(id);
        return ResponseEntity.ok(animal);
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
}
