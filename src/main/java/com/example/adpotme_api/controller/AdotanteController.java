package com.example.adpotme_api.controller;

import com.example.adpotme_api.adapter.AdotanteAdapter;
import com.example.adpotme_api.adapter.AdotanteAdapterImp;
import com.example.adpotme_api.adapter.AdotanteAdapterTarget;
import com.example.adpotme_api.adapter.AdotanteTarget;
import com.example.adpotme_api.dto.adotante.*;
import com.example.adpotme_api.dto.formulario.FormularioCreateDto;
import com.example.adpotme_api.dto.formulario.FormularioResponseAdotanteDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.mapper.AdotanteMapper;
import com.example.adpotme_api.mapper.FormularioMapper;
import com.example.adpotme_api.service.AdotanteService;
import com.example.adpotme_api.service.FormularioService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/adotantes")
@Tag(name = "Adotante", description = "Controlador para operações relacionadas aos adotantes.")
@SecurityRequirement(name = "bearerAuth")
public class AdotanteController {

    @Autowired
    private AdotanteService adotanteService;
    @Autowired
    private FormularioService formularioService;

    @PostMapping(value = "/cadastrar", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Cadastra um adotante", description = "Cadastra um novo adotante no sistema com os dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "O adotante foi cadastrado com sucesso no sistema.")
    @ApiResponse(responseCode = "400", description = "Erro de validação.")

    public ResponseEntity<AdotanteResponseDto> cadastrarAdotante(
            @RequestPart("adotante") @Valid String adotanteJson,
            @RequestPart(value = "fotoPerfil", required = false) MultipartFile fotoPerfil
    ) throws JsonProcessingException {
        AdotanteTarget adotanteTarget = new AdotanteAdapterTarget(new AdotanteAdapterImp());
        AdotanteCreateDto dados = adotanteTarget.request(adotanteJson);

        AdotanteResponseDto adotante = adotanteService.cadastrarAdotante(dados, fotoPerfil);

        return ResponseEntity.status(201).body(adotante);
    }
    @PostMapping("preencher-formulario/{idAdotante}")
    @Operation(
            summary = "Preenche um novo formulário.",
            description = "Este endpoint permite que um usuário preencha um novo formulário, " +
                    "enviando os dados necessários para a criação e recebendo o formulário preenchido como resposta."
    )
    @ApiResponse(responseCode = "201", description = "Formulário preenchido com sucesso.")
    @ApiResponse(responseCode = "400", description = "Erro de validação.")
    public ResponseEntity<FormularioResponseAdotanteDto> preencherFormulario(@RequestBody @Valid FormularioCreateDto dados, @PathVariable Long idAdotante) {
        Formulario formulario = formularioService.preencherFormulario(dados, idAdotante);
        FormularioResponseAdotanteDto formularioResponse = FormularioMapper.toResponseDto(formulario);
        return ResponseEntity.status(201).body(formularioResponse);
    }

    @PutMapping(value = "atualizar-foto-adotante/{idAdotante}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(
            summary = "Atualiza a foto de perfil de um adotante.",
            description = "Este endpoint permite que um usuário atualize a foto de perfil de um adotante, " +
                    "enviando a nova foto de perfil e recebendo o adotante atualizado como resposta."
    )
    @ApiResponse(responseCode = "200", description = "Foto de perfil atualizada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante não encontrado.")
    public ResponseEntity<AdotanteResponseDto> atualizarFotoAdotante(@RequestPart(value = "fotoPerfil", required = false) MultipartFile fotoPerfil, @PathVariable Long idAdotante) {
        Adotante adotante = adotanteService.atualizarFotoAdotante(fotoPerfil, idAdotante);
        AdotanteResponseDto adotanteResponse = AdotanteMapper.toResponseDto(adotante);
        return ResponseEntity.ok(adotanteResponse);
    }

    @GetMapping
    @Operation(summary = "Retorna todos os adotantes", description = "Recupera uma lista de todos os adotantes cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "A lista de adotantes foi recuperada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Nenhum adotante foi encontrado.")
    public ResponseEntity<List<AdotanteResponseDto>> recuperarAdotantes() {
        List<AdotanteResponseDto> adotantes = adotanteService.recuperarAdotantes();
        if (adotantes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(adotantes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um adotante pelo ID", description = "Recupera os detalhes de um adotante específico com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "O adotante foi encontrado e seus detalhes foram retornados.")
    @ApiResponse(responseCode = "404", description = "Adotante não encontrado.")
    public ResponseEntity<Adotante> recuperarAdotantePorId(@PathVariable Long id) {
        Adotante adotante = adotanteService.recuperarAdotantePorId(id);
        return ResponseEntity.ok(adotante);
    }

    @GetMapping("/ordenados-por-estado")
    @Operation(summary = "Retorna todos os adotantes ordenados por estado", description = "Recupera uma lista de adotantes organizados de acordo com seus estados.")
    @ApiResponse(responseCode = "200", description = "A lista de adotantes ordenados por estado foi recuperada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Nenhum adotante foi encontrado.")
    public ResponseEntity<List<AdotanteResponseDto>> recuperarAdotantesOrdenadosPorEstado() {
        List<AdotanteResponseDto> adotantes = adotanteService.recuperarAdotantesOrdenadosPorEstado();
        if(adotantes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(adotantes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um adotante", description = "Atualiza as informações de um adotante existente com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Os dados do adotante foram atualizados com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante não encontrado.")
    @ApiResponse(responseCode = "400", description = "Erro de validação.")

    public ResponseEntity<AdotanteResponseDto> atualizarAdotante(@PathVariable Long id, @RequestBody @Valid AdotanteUpdateDto adotante) {
        Adotante atualizado = adotanteService.atualizarAdotante(id, adotante);
        AdotanteResponseDto adotanteResponse = AdotanteMapper.toResponseDto(atualizado);
        return ResponseEntity.ok(adotanteResponse);
    }
    @PutMapping("atualizacao-formulario/{id}/")
    @Operation(
            summary = "Atualiza um formulário.",
            description = "Este endpoint permite que um usuário atualize um formulário, " +
                    "enviando os dados necessários para a atualização e recebendo o formulário atualizado como resposta."
    )
    @ApiResponse(responseCode = "200", description = "Formulário atualizado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante não encontrado.")
    @ApiResponse(responseCode = "400", description = "Erro de validação.")
    public ResponseEntity<AdotanteResponseDto> atualizarFormulario(@RequestBody @Valid FormularioCreateDto dados, @PathVariable Long id) {
        AdotanteResponseDto adotante = formularioService.atualizarFormulario(dados, id);
        return ResponseEntity.status(200).body(adotante);
    }
    @GetMapping("form-adotante/{id}")
    @Operation(
            summary = "Recupera o formulário de um adotante.",
            description = "Este endpoint permite que um usuário recupere o formulário de um adotante, " +
                    "enviando o ID do adotante e recebendo o formulário preenchido como resposta."
    )
    @ApiResponse(responseCode = "200", description = "Formulário recuperado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante não encontrado.")
    public ResponseEntity<AdotanteFormularioDto> recuperarFormularioAdotante(@PathVariable Long id) {
        AdotanteFormularioDto adotanteForm = formularioService.recuperarAdotanteForm(id);
        return ResponseEntity.ok(adotanteForm);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um adotante", description = "Remove um adotante específico do sistema com base no ID fornecido.")
    @ApiResponse(responseCode = "204", description = "O adotante foi removido do sistema com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante não encontrado.")
    public ResponseEntity<Void> deletarAdotante(@PathVariable Long id) {
        adotanteService.deletarAdotante(id);
        return ResponseEntity.status(204).build();
    }
    @GetMapping("formulario-adotante/{id}")
    @Operation(
            summary = "Recupera o formulário de um adotante.",
            description = "Este endpoint permite que um usuário recupere o formulário de um adotante, " +
                    "enviando o ID do adotante e recebendo o formulário preenchido como resposta."
    )
    @ApiResponse(responseCode = "200", description = "Formulário recuperado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante não encontrado.")
    public ResponseEntity<FormularioResponseAdotanteDto> recuperarFormularioAdotanteUser(@PathVariable Long id) {
        FormularioResponseAdotanteDto formulario = formularioService.recuperarFormularioAdotante(id);
        return ResponseEntity.ok(formulario);
    }

    @GetMapping("dados-foto-adotante/{id}")
    @Operation(
            summary = "Recupera os dados de um adotante com a URL da foto de perfil.",
            description = "Este endpoint permite que um usuário recupere os dados de um adotante, " +
                    "enviando o ID do adotante e recebendo os dados com a URL da foto de perfil."
    )
    @ApiResponse(responseCode = "200", description = "Dados do adotante recuperados com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante não encontrado.")
    public ResponseEntity<AdotanteDadosFoto> recuperarAdotanteDadosFoto(@PathVariable Long id) {
        AdotanteDadosFoto adotante = adotanteService.recuperarAdotanteDadosFoto(id);
        return ResponseEntity.ok(adotante);
    }

    @GetMapping("animais-favoritos-usuario/{id}")
    @Operation(
            summary = "Recupera os animais favoritos de um adotante.",
            description = "Este endpoint permite que um usuário recupere os animais favoritos de um adotante, " +
                    "enviando o ID do adotante e recebendo a lista de animais favoritos."
    )
    @ApiResponse(responseCode = "200", description = "Animais favoritos recuperados com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante não encontrado.")
    public ResponseEntity<AnimalFavoritoUsuarioDto> recuperarAnimaisFavoritosAdotante(@PathVariable Long id) {
        AnimalFavoritoUsuarioDto adotante = adotanteService.recuperarAnimaisFavoritosAdotante(id);

        return ResponseEntity.ok(adotante);
    }
    @GetMapping("ong-favoritas-usuario/{id}")
    @Operation(
            summary = "Recupera as ONGs favoritas de um adotante.",
            description = "Este endpoint permite que um usuário recupere as ONGs favoritas de um adotante, " +
                    "enviando o ID do adotante e recebendo a lista de ONGs favoritas."
    )
    @ApiResponse(responseCode = "200", description = "ONGs favoritas recuperadas com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante não encontrado.")
    public ResponseEntity<AdotanteFavoritoOngDto> recuperarOngsFavoritasAdotante(@PathVariable Long id) {
        AdotanteFavoritoOngDto adotante = adotanteService.recuperarOngsFavoritasAdotante(id);
        return ResponseEntity.ok(adotante);
    }

    @PostMapping("favoritar-animal{idAdotante}/{idAnimal}")
    @Operation(
            summary = "Favorita um animal.",
            description = "Este endpoint permite que um usuário favorita um animal, " +
                    "enviando o ID do adotante e o ID do animal."
    )
    @ApiResponse(responseCode = "201", description = "Animal favoritado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante ou animal não encontrado.")
    public ResponseEntity<?> favoritarAnimal(@PathVariable Long idAdotante, @PathVariable Long idAnimal) {
        adotanteService.favoritarAnimal(idAdotante, idAnimal);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("desfavoritar-animal/{idAdotante}/{idAnimal}")
    @Operation(
            summary = "Desfavorita um animal.",
            description = "Este endpoint permite que um usuário desfavorita um animal, " +
                    "enviando o ID do adotante e o ID do animal."
    )
    @ApiResponse(responseCode = "204", description = "Animal desfavoritado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante ou animal não encontrado.")
    public ResponseEntity<Void> desfavoritarAnimal(@PathVariable Long idAdotante, @PathVariable Long idAnimal) {
        adotanteService.desfavoritarAnimal(idAdotante, idAnimal);
        return ResponseEntity.status(204).build();
    }

    @PostMapping("favoritar-ong/{idAdotante}/{idOng}")
    @Operation(
            summary = "Favorita uma ONG.",
            description = "Este endpoint permite que um usuário favorita uma ONG, " +
                    "enviando o ID do adotante e o ID da ONG."
    )
    @ApiResponse(responseCode = "201", description = "ONG favoritada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante ou ONG não encontrado.")
    public ResponseEntity<Void> favoritarOng(@PathVariable Long idAdotante, @PathVariable Long idOng) {
        adotanteService.favoritarOng(idAdotante, idOng);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/lista-requisicao-adotante/{idAdotante}")
    @Operation(
            summary = "Lista as requisições de adoção de um adotante.",
            description = "Este endpoint permite que um usuário recupere a lista de requisições de adoção de um adotante, " +
                    "enviando o ID do adotante e recebendo a lista de requisições."
    )
    @ApiResponse(responseCode = "200", description = "Requisições de adoção recuperadas com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante não encontrado.")
    public ResponseEntity<List<AdotanteDtoListaRequisicao>> listarRequisicoesAdotante(@PathVariable Long idAdotante) {
        List<AdotanteDtoListaRequisicao> requisicoes = adotanteService.listarRequisicoesAdotante(idAdotante);
        return ResponseEntity.ok(requisicoes);
    }


    @GetMapping("/me")
    public ResponseEntity<AdotanteUserDto> adotanteAutenticado() {
        // Tem como fazer isto com uma anotação @AuthenticationPrincipal

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof Adotante adotanteAtual) {
            AdotanteUserDto dto = AdotanteMapper.toAdotanteAutenticadoDto(adotanteAtual);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
