package com.example.adpotme_api.petApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatFactService {

    @Autowired
    private RestTemplate restTemplate;

    public CatFactResponse getCatFact() {
        String url = "https://catfact.ninja/fact";
        return restTemplate.getForObject(url, CatFactResponse.class);
    }
}
