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

@CrossOrigin(origins = "*")
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



}
