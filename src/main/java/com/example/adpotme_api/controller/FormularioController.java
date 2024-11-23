package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.formulario.FormularioResponseAdotanteDto;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.dto.formulario.FormularioCreateDto;
import com.example.adpotme_api.entity.requisicao.Status;
import com.example.adpotme_api.service.FormularioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/formularios")
@Tag(name = "Formulario" , description = "Controlador para operações relacionadas aos formulários de adoção.")
@SecurityRequirement(name = "bearerAuth")
public class FormularioController {

    @Autowired
    private FormularioService formularioService;




    @GetMapping
    @Operation(
            summary = "Lista todos os formulários.",
            description = "Este endpoint retorna uma lista de todos os formulários disponíveis. " +
                    "Se não houver formulários, retorna um status 204 (Sem Conteúdo)."
    )
    @ApiResponse(responseCode = "200", description = "Lista de formulários retornada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Não há formulários cadastrados.")
    public ResponseEntity<List<Formulario>> listarFormularios() {
        List<Formulario> formularios = formularioService.listarFormularios();
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("/formulario-aceito")
    @Operation(
            summary = "Lista formulários aceitos.",
            description = "Este endpoint retorna todos os formulários que foram aceitos. " +
                    "Caso não haja formulários aceitos, retorna um status 204 (Sem Conteúdo)."
    )
    @ApiResponse(responseCode = "200", description = "Lista de formulários aceitos retornada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Não há formulários aceitos.")
    public ResponseEntity<List<Formulario>> listarFormulariosAceitos() {
        List<Formulario> formularios = formularioService.listarFormulariosPorStatus(Status.APROVADO);
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("/formulario-reprovado")
    @Operation(
            summary = "Lista formulários reprovados.",
            description = "Este endpoint retorna todos os formulários que foram reprovados. " +
                    "Se não houver formulários reprovados, retorna um status 204 (Sem Conteúdo)."
    )
    @ApiResponse(responseCode = "200", description = "Lista de formulários reprovados retornada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Não há formulários reprovados.")
    public ResponseEntity<List<Formulario>> listarFormulariosReprovados() {
        List<Formulario> formularios = formularioService.listarFormulariosPorStatus(Status.DESCARTADO);
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("/formulario-inicio")
    @Operation(
            summary = "Lista formulários em início de aplicação.",
            description = "Este endpoint retorna todos os formulários que estão no início da aplicação. " +
                    "Caso não haja formulários nesta condição, retorna um status 204 (Sem Conteúdo)."
    )
    @ApiResponse(responseCode = "200", description = "Lista de formulários em início de aplicação retornada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Não há formulários no início da aplicação.")
    public ResponseEntity<List<Formulario>> listarFormulariosInicioAplicacao() {
        List<Formulario> formularios = formularioService.listarFormulariosPorStatus(Status.NOVA);
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("/formulario-em-revisao")
    @Operation(
            summary = "Lista formulários em revisão.",
            description = "Este endpoint retorna todos os formulários que estão em revisão. " +
                    "Se não houver formulários nesta condição, retorna um status 204 (Sem Conteúdo)."
    )
    @ApiResponse(responseCode = "200", description = "Lista de formulários em revisão retornada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Não há formulários em revisão.")
    public ResponseEntity<List<Formulario>> listarFormularioRevisao() {
        List<Formulario> formularios = formularioService.listarFormulariosPorStatus(Status.REVISAO);
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("/formulario-em-documentacao")
    @Operation(
            summary = "Lista formulários em documentação.",
            description = "Este endpoint retorna todos os formulários que estão na fase de documentação. " +
                    "Caso não haja formulários nesta condição, retorna um status 204 (Sem Conteúdo)."
    )
    @ApiResponse(responseCode = "200", description = "Lista de formulários em documentação retornada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Não há formulários em documentação.")
    public ResponseEntity<List<Formulario>> listarFormulariosDocumentacao() {
        List<Formulario> formularios = formularioService.listarFormulariosPorStatus(Status.DOCUMENTACAO);
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("/formulario-adotado")
    @Operation(
            summary = "Lista formulários adotados.",
            description = "Este endpoint retorna todos os formulários que foram adotados. " +
                    "Se não houver formulários adotados, retorna um status 204 (Sem Conteúdo)."
    )
    @ApiResponse(responseCode = "200", description = "Lista de formulários adotados retornada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Não há formulários adotados.")
    public ResponseEntity<List<Formulario>> listarFormulariosAdotado() {
        List<Formulario> formularios = formularioService.listarFormulariosPorStatus(Status.CONCLUIDO);
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Lista formulários por adotante.",
            description = "Este endpoint retorna todos os formulários associados a um adotante específico, " +
                    "identificado pelo seu ID. Se não houver formulários, retorna um status 204 (Sem Conteúdo)."
    )
    @ApiResponse(responseCode = "200", description = "Lista de formulários por adotante retornada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Adotante não encontrado.")
    public ResponseEntity<FormularioResponseAdotanteDto> listarFormulariosPorAdotante(@PathVariable Long id) {
        FormularioResponseAdotanteDto formulario = formularioService.formulariosPorId(id);

        return ResponseEntity.ok(formulario);
    }
}
