package com.example.adpotme_api.controller;

import com.example.adpotme_api.adotante.Adotante;
import com.example.adpotme_api.adotante.AdotanteRepository;
import com.example.adpotme_api.endereco.Endereco;
import com.example.adpotme_api.endereco.EnderecoDto;
import com.example.adpotme_api.endereco.EnderecoRepository;
import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.ong.OngRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
@Tag(name = "Endereço")
public class EnderecoController {

    private static final Logger log = LoggerFactory.getLogger(EnderecoController.class);
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    AdotanteRepository adotanteRepository;
    @Autowired
    OngRepository ongRepository;

    @PostMapping("cadastrar-endereco-ong/{idOng}")
    @Operation(summary = "Associa um endereço a uma ONG", description = """
      # Associa o endereço da ONG a partir da API externa ViaCep
      ---
      Cadastra o endereço da ONG
      """)
    @ApiResponse(responseCode = "200", description = "Dados de endereço")
    public ResponseEntity<Ong> cadastrarEndereco(@RequestParam String cep, @PathVariable Long idOng) {

        RestClient client = RestClient.builder()
                .baseUrl("https://viacep.com.br/ws/")
                .messageConverters(httpMessageConverters -> httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build();

        String raw = client.get()
                .uri(cep + "/json")
                .retrieve()
                .body(String.class);

        log.info("Resposta da API: " + raw);

        Endereco endereco = client.get()
                .uri(cep + "/json")
                .retrieve()
                .body(Endereco.class);

        if (endereco == null) {
            return ResponseEntity.noContent().build();
        }

        Optional<Ong> ongOpt = ongRepository.findById(idOng);

        if(ongOpt.isPresent()) {
            Endereco resposta = new Endereco();
            resposta.setBairro(endereco.getBairro());
            resposta.setCep(endereco.getCep());
            resposta.setCidade(endereco.getCidade());
            resposta.setEstado(endereco.getEstado());
            resposta.setRua(endereco.getRua());

            enderecoRepository.save(resposta);
            ongOpt.get().setEndereco(resposta);

            ongRepository.save(ongOpt.get());

            Ong ong = ongOpt.get();
            return ResponseEntity.ok(ong);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("cadastrar-endereco-adotante/{idAdotante}")
    @Operation(summary = "Associa um endereço a um adotante", description = """
      # Associa o endereço do adotante a partir da API externa ViaCep
      ---
      Cadastra o endereço do adotante
      """)
    @ApiResponse(responseCode = "200", description = "Dados de endereço")
    public ResponseEntity<Endereco> buscarEndereco(@RequestParam String cep, @PathVariable Long idAdotante) {

        RestClient client = RestClient.builder()
                .baseUrl("https://viacep.com.br/ws/")
                .messageConverters(httpMessageConverters -> httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build();

        String raw = client.get()
                .uri(cep + "/json")
                .retrieve()
                .body(String.class);

        log.info("Resposta da API: " + raw);

        Endereco endereco = client.get()
                .uri(cep + "/json")
                .retrieve()
                .body(Endereco.class);

        if (endereco == null) {
            return ResponseEntity.noContent().build();
        }

        Optional<Adotante> adotante = adotanteRepository.findById(idAdotante);

        if(adotante.isPresent()) {
            Endereco resposta = new Endereco();
            resposta.setBairro(endereco.getBairro());
            resposta.setCep(endereco.getCep());
            resposta.setCidade(endereco.getCidade());
            resposta.setEstado(endereco.getEstado());
            resposta.setRua(endereco.getRua());

            enderecoRepository.save(resposta);
            adotante.get().setEndereco(resposta);

            adotanteRepository.save(adotante.get());
            return ResponseEntity.ok(resposta);
        }

            return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Buscar dados do endereço", description = """
      # Busca os dados de um endereço a partir do CEP utilizando uma API externa
      ---
      Retorna os dados de endereço retornados da API.
      """)
    @ApiResponse(responseCode = "200", description = "Dados de endereço")
    public ResponseEntity<EnderecoDto> buscarEndereco(@RequestParam String cep) {

        RestClient client = RestClient.builder()
                .baseUrl("https://viacep.com.br/ws/")
                .messageConverters(httpMessageConverters -> httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build();

        String raw = client.get()
                .uri(cep + "/json")
                .retrieve()
                .body(String.class);

        log.info("Resposta da API: " + raw);

        Endereco endereco = client.get()
                .uri(cep + "/json")
                .retrieve()
                .body(Endereco.class);

        if (endereco == null) {
            return ResponseEntity.noContent().build();
        }

        EnderecoDto resposta = new EnderecoDto();
        resposta.setBairro(endereco.getBairro());
        resposta.setCep(endereco.getCep());
        resposta.setCidade(endereco.getCidade());
        resposta.setEstado(endereco.getEstado());
        resposta.setRua(endereco.getRua());

        return ResponseEntity.ok(resposta);
    }
}