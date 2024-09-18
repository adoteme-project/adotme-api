package com.example.adpotme_api.service;

import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.dto.endereco.EnderecoDto;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.repository.AdotanteRepository;
import com.example.adpotme_api.repository.EnderecoRepository;
import com.example.adpotme_api.repository.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestClient;
import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private AdotanteRepository adotanteRepository;

    @Autowired
    private OngRepository ongRepository;

    private RestClient getViaCepClient() {
        return RestClient.builder()
                .baseUrl("https://viacep.com.br/ws/")
                .messageConverters(httpMessageConverters -> httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build();
    }

    public Endereco buscarEnderecoViaCep(String cep) {
        try {
            RestClient client = getViaCepClient();
            return client.get()
                    .uri(cep + "/json")
                    .retrieve()
                    .body(Endereco.class);
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao buscar endereço no ViaCep", e);
        }
    }

    @Transactional
    public Endereco atualizarEnderecoParaOng(String cep, Long idOng) {
        Endereco endereco = buscarEnderecoViaCep(cep);

        Optional<Ong> ongOpt = ongRepository.findById(idOng);
        if (ongOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }

        Ong ong = ongOpt.get();
        Endereco novoEndereco = ong.getEndereco();
        novoEndereco.setBairro(endereco.getBairro());
        novoEndereco.setCep(endereco.getCep());
        novoEndereco.setCidade(endereco.getCidade());
        novoEndereco.setEstado(endereco.getEstado());
        novoEndereco.setRua(endereco.getRua());
        enderecoRepository.save(novoEndereco);

        ong.setEndereco(novoEndereco);

        return novoEndereco;
    }

    @Transactional
    public Endereco atualizarEnderecoParaAdotante(String cep, Long idAdotante) {
        Endereco endereco = buscarEnderecoViaCep(cep);

        Optional<Adotante> adotanteOpt = adotanteRepository.findById(idAdotante);
        if (adotanteOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }
        Adotante adotante = adotanteOpt.get();
        Endereco novoEndereco = adotante.getEndereco();
        novoEndereco.setBairro(endereco.getBairro());
        novoEndereco.setCep(endereco.getCep());
        novoEndereco.setCidade(endereco.getCidade());
        novoEndereco.setEstado(endereco.getEstado());
        novoEndereco.setRua(endereco.getRua());
        enderecoRepository.save(novoEndereco);

        adotante.setEndereco(novoEndereco);
        adotanteRepository.save(adotante);

        return novoEndereco;
    }

    public EnderecoDto buscarEnderecoPorCep(String cep) {
        Endereco endereco = buscarEnderecoViaCep(cep);
        if (endereco == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }

        EnderecoDto resposta = new EnderecoDto();
        resposta.setBairro(endereco.getBairro());
        resposta.setCep(endereco.getCep());
        resposta.setCidade(endereco.getCidade());
        resposta.setEstado(endereco.getEstado());
        resposta.setRua(endereco.getRua());
        return resposta;
    }
}
