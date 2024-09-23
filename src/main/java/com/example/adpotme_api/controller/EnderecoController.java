package com.example.adpotme_api.controller;

import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.dto.endereco.EnderecoDto;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/enderecos")
@Tag(name = "Endereço")
@SecurityRequirement(name = "bearerAuth")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PutMapping("endereco-ong/{idOng}")
    @Operation(summary = "Atualiza o endereço de uma ONG", description = """
      # Atualiza o endereço da ONG a partir da API externa ViaCep
      --- 
      Atualiza o endereço da ONG
      """)
    @ApiResponse(responseCode = "200", description = "Dados de endereço")
    public ResponseEntity<Endereco> atualizarEnderecoParaOng(@RequestParam String cep, @PathVariable Long idOng) {
        Endereco endereco = enderecoService.atualizarEnderecoParaOng(cep, idOng);
        return ResponseEntity.ok(endereco);
    }

    @PutMapping("endereco-adotante/{idAdotante}")
    @Operation(summary = "Atualiza o endereço de um adotante", description = """
      # Atualiza o endereço do adotante a partir da API externa ViaCep
      --- 
    Atualiza o endereço do adotante
      """)
    @ApiResponse(responseCode = "200", description = "Dados de endereço")
    public ResponseEntity<Endereco> atualizarEnderecoParaAdotante(@RequestParam String cep, @PathVariable Long idAdotante) {
        Endereco endereco = enderecoService.atualizarEnderecoParaAdotante(cep, idAdotante);
        return ResponseEntity.ok(endereco);
    }

    @GetMapping
    @Operation(summary = "Buscar dados do endereço", description = """
      # Busca os dados de um endereço a partir do CEP utilizando uma API externa
      --- 
      Retorna os dados de endereço retornados da API.
      """)
    @ApiResponse(responseCode = "200", description = "Dados de endereço")
    public ResponseEntity<EnderecoDto> buscarEndereco(@RequestParam String cep) {
        EnderecoDto resposta = enderecoService.buscarEnderecoPorCep(cep);
        return ResponseEntity.ok(resposta);
    }
}
