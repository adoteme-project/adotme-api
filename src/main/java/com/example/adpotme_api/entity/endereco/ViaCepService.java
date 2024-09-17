package com.example.adpotme_api.entity.endereco;

import com.example.adpotme_api.repository.AdotanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    @Autowired
    AdotanteRepository adotanteRepository;


        public Endereco obterEnderecoPorCep(String cep) {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            RestTemplate restTemplate = new RestTemplate();
            Endereco response = restTemplate.getForObject(url, Endereco.class);

            if (response == null) {
                throw new IllegalArgumentException("CEP inválido");
            }

            return response;
        }
    }

