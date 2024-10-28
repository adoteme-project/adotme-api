package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.requisicao.RequisicaoCreateDto;
import com.example.adpotme_api.dto.requisicao.RequisicaoReadDto;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.service.RequisicaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requisicoes")
@CrossOrigin
@Tag(name = "Requisição", description = "Controlador para operações relacionadas às requisições de adoção.")
@SecurityRequirement(name = "bearerAuth")
public class RequisicaoController {
    @Autowired
    private RequisicaoService requisicaoService;

    @PostMapping
    public ResponseEntity<Requisicao> criarRequisicao(@RequestBody RequisicaoCreateDto requisicaoCreateDto) {
        Requisicao requisicao = requisicaoService.criarRequisicao(requisicaoCreateDto);
        return ResponseEntity.ok(requisicao);
    }
    @GetMapping
    public ResponseEntity<List<RequisicaoReadDto>> listarRequisicoes() {
        List<RequisicaoReadDto> requisicoes = requisicaoService.listarRequisicoes();
        if(requisicoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(requisicoes);
    }

    @PatchMapping("aprovado/{id}")
    public ResponseEntity<Requisicao> atualizarStatusRequisicaoParaAprovado(@PathVariable Long id) {
        Requisicao requisicaoAtualizada = requisicaoService.atualizarRequisicaoAprovado(id);
        return ResponseEntity.ok(requisicaoAtualizada);
    }
    @PatchMapping("reprovado/{id}")
    public ResponseEntity<Requisicao> atualizarStatusRequisicaoParaReprovado(@PathVariable Long id) {
        Requisicao requisicaoAtualizada = requisicaoService.atualizarRequisicaoReprovado(id);
        return ResponseEntity.ok(requisicaoAtualizada);
    }
    @PatchMapping("documentacao/{id}")
    public ResponseEntity<Requisicao> atualizarStatusRequisicaoParaDocumentacao(@PathVariable Long id) {
        Requisicao requisicaoAtualizada = requisicaoService.atualizarRequisicaoDocumentacao(id);
        return ResponseEntity.ok(requisicaoAtualizada);
    }
    @PatchMapping("adotado/{id}")
    public ResponseEntity<Requisicao> atualizarStatusRequisicaoParaAdotado(@PathVariable Long id) {
        Requisicao requisicaoAtualizada = requisicaoService.atualizarRequisicaoAdotado(id);
        return ResponseEntity.ok(requisicaoAtualizada);
    }
}
