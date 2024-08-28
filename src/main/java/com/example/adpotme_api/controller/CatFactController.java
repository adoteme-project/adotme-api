package com.example.adpotme_api.controller;

import com.example.adpotme_api.petApi.CatFactResponse;
import com.example.adpotme_api.petApi.CatFactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CatFactController {

    @Autowired
    private CatFactService catFactService;

    @GetMapping("/fact")
    public CatFactResponse getCatFact() {
        return catFactService.getCatFact();
    }
}
