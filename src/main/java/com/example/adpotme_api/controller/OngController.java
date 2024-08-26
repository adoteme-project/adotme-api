package com.example.adpotme_api.controller;

import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.ong.OngCreateDto;
import com.example.adpotme_api.ong.OngRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ongs")
public class OngController {


    @Autowired
    private OngRepository ongRepository;

    @PostMapping
    @Transactional
    public void cadastrarOng(@RequestBody @Valid OngCreateDto dados){
        ongRepository.save(new Ong(dados));
    }
}
