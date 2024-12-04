package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.adotante.AdotanteRequisicaoDto;
import com.example.adpotme_api.dto.requisicao.RequisicaoCreateDto;
import com.example.adpotme_api.dto.requisicao.RequisicaoReadDto;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.service.RequisicaoService;
import com.example.adpotme_api.util.FilaObj;
import com.example.adpotme_api.util.PilhaObj;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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

    private final PilhaObj<Requisicao> pilhaRequisicoes = new PilhaObj<>(100);
    private final FilaObj<Requisicao> filaRequisicoes = new FilaObj<>(100);


    @PostMapping
    public ResponseEntity<Requisicao> criarRequisicao(@RequestBody RequisicaoCreateDto requisicaoCreateDto) {
        Requisicao requisicao = requisicaoService.criarRequisicao(requisicaoCreateDto);
        filaRequisicoes.insert(requisicao);

        pilhaRequisicoes.push(requisicao);
        return ResponseEntity.ok(requisicao);
    }

    @GetMapping("/processar")
    public ResponseEntity<Requisicao> processarProximaRequisicao() {
        Requisicao proximaRequisicao = filaRequisicoes.poll();
        if (proximaRequisicao != null) {
            return ResponseEntity.ok(proximaRequisicao);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/desfazer")
    public ResponseEntity<Requisicao> desfazerUltimaRequisicao() {
        Requisicao ultimaRequisicao = pilhaRequisicoes.pop();
        if (ultimaRequisicao != null) {
            requisicaoService.deletarRequisicao(ultimaRequisicao.getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<RequisicaoReadDto>> listarRequisicoes() {
        List<RequisicaoReadDto> requisicoes = requisicaoService.listarRequisicoes();
        if(requisicoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(requisicoes);
    }
    @GetMapping("/{idAdotante}")
    public ResponseEntity<AdotanteRequisicaoDto> requisicaoAdotante(@PathVariable Long idAdotante) {
        AdotanteRequisicaoDto requisicao = requisicaoService.requisicaoAdotante(idAdotante);
        return ResponseEntity.ok(requisicao);
    }

    @PatchMapping("aprovado/{id}")
    public ResponseEntity<Requisicao> atualizarStatusRequisicaoParaAprovado(@PathVariable Long id) {
        Requisicao requisicaoAtualizada = requisicaoService.atualizarRequisicaoAprovado(id);
        return ResponseEntity.ok(requisicaoAtualizada);
    }
    @PatchMapping("reprovado/{id}")
    public ResponseEntity<Requisicao> atualizarStatusRequisicaoParaReprovado(@PathVariable Long id, @RequestParam String motivo) {
        Requisicao requisicaoAtualizada = requisicaoService.atualizarRequisicaoReprovado(id, motivo);
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
