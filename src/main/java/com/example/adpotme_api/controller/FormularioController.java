package com.example.adpotme_api.controller;

import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.dto.formulario.FormularioCreateDto;
import com.example.adpotme_api.entity.requisicao.Status;
import com.example.adpotme_api.service.FormularioService;
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
@Tag(name = "Formulario")
@SecurityRequirement(name = "bearerAuth")
public class FormularioController {

    @Autowired
    private FormularioService formularioService;

    @PostMapping
    public ResponseEntity<Formulario> preencherFormulario(@RequestBody @Valid FormularioCreateDto dados) {
        Formulario formulario = formularioService.preencherFormulario(dados);
        return ResponseEntity.status(201).body(formulario);
    }

    @GetMapping
    public ResponseEntity<List<Formulario>> listarFormularios() {
        List<Formulario> formularios = formularioService.listarFormularios();
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("/formulario-aceito")
    public ResponseEntity<List<Formulario>> listarFormulariosAceitos() {
        List<Formulario> formularios = formularioService.listarFormulariosPorStatus(Status.APROVADO);
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("/formulario-reprovado")
    public ResponseEntity<List<Formulario>> listarFormulariosReprovados() {
        List<Formulario> formularios = formularioService.listarFormulariosPorStatus(Status.REPROVADO);
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("/formulario-inicio")
    public ResponseEntity<List<Formulario>> listarFormulariosInicioAplicacao() {
        List<Formulario> formularios = formularioService.listarFormulariosPorStatus(Status.INICIO_DA_APLICACAO);
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("/formulario-em-revisao")
    public ResponseEntity<List<Formulario>> listarFormularioRevisao() {
        List<Formulario> formularios = formularioService.listarFormulariosPorStatus(Status.REVISAO);
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("/formulario-em-documentacao")
    public ResponseEntity<List<Formulario>> listarFormulariosDocumentacao() {
        List<Formulario> formularios = formularioService.listarFormulariosPorStatus(Status.DOCUMENTACAO);
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("/formulario-adotado")
    public ResponseEntity<List<Formulario>> listarFormulariosAdotado() {
        List<Formulario> formularios = formularioService.listarFormulariosPorStatus(Status.ADOTADO);
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }

    @GetMapping("adotante/{id}")
    public ResponseEntity<List<Formulario>> listarFormulariosPorAdotante(@PathVariable Long id) {
        List<Formulario> formularios = formularioService.listarFormulariosPorAdotante(id);
        if (formularios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(formularios);
    }
}
