package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.adocao.AdocaoDados;
import com.example.adpotme_api.entity.adocao.LogAdocao;
import com.example.adpotme_api.service.AdocaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/adocoes")
@Tag(name = "Adocao" , description = "Controlador para operações relacionadas as adocoes de uma ong.")
@SecurityRequirement(name = "bearerAuth")
public class AdocaoController {

    @Autowired
    private AdocaoService adocaoService;

        @GetMapping("dados-adocao/{idOng}")
            public ResponseEntity<List<LogAdocao>> listarAdocoes(@PathVariable Long idOng){
                List<LogAdocao> adocoes = adocaoService.listarAdocoes(idOng);
                if (adocoes.isEmpty()) {
                    return ResponseEntity.status(204).build();
                }
                return ResponseEntity.ok(adocoes);
            }

            @GetMapping("dados-adocao-dashboard/{idOng}")
            public ResponseEntity<List<AdocaoDados>> listarAdocoesDashboard(@PathVariable Long idOng, @RequestParam Integer ano){
                List<AdocaoDados> dados = adocaoService.listarAdocoesDashboard(idOng, ano);

                return ResponseEntity.ok(dados);
            }


}
