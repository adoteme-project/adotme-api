package com.example.adpotme_api.controller;

import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.dto.endereco.EnderecoDto;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
@Tag(name = "Endereço")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping("cadastrar-endereco-ong/{idOng}")
    @Operation(summary = "Associa um endereço a uma ONG", description = """
      # Associa o endereço da ONG a partir da API externa ViaCep
      --- 
      Cadastra o endereço da ONG
      """)
    @ApiResponse(responseCode = "200", description = "Dados de endereço")
    public ResponseEntity<Ong> cadastrarEnderecoParaOng(@RequestParam String cep, @PathVariable Long idOng) {
        Ong ong = enderecoService.cadastrarEnderecoParaOng(cep, idOng);
        return ResponseEntity.ok(ong);
    }

    @PostMapping("cadastrar-endereco-adotante/{idAdotante}")
    @Operation(summary = "Associa um endereço a um adotante", description = """
      # Associa o endereço do adotante a partir da API externa ViaCep
      --- 
      Cadastra o endereço do adotante
      """)
    @ApiResponse(responseCode = "200", description = "Dados de endereço")
    public ResponseEntity<Endereco> cadastrarEnderecoParaAdotante(@RequestParam String cep, @PathVariable Long idAdotante) {
        Endereco endereco = enderecoService.cadastrarEnderecoParaAdotante(cep, idAdotante);
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
