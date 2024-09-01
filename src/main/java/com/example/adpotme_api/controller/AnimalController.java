package com.example.adpotme_api.controller;

import com.example.adpotme_api.animal.*;
import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.ong.OngRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private OngRepository ongRepository;

    @PostMapping("/cachorro")
    @Transactional
    public ResponseEntity<?> cadastrarCachorro(@RequestBody @Valid CachorroCreateDto cachorroDto) {
        Ong ong = ongRepository.findById(cachorroDto.getOngId())
                .orElseThrow(() -> new RuntimeException("ONG não encontrada"));



                Animal animal;


                Cachorro cachorro = new Cachorro(cachorroDto);
                cachorro.setOng(ong);
                cachorro.calcularTaxaAdocao();
                animal = cachorro;
        animalRepository.save(animal);
        return ResponseEntity.ok("Animal cadastrado com sucesso.");
    }
    @PostMapping("/gato")
    @Transactional
    public ResponseEntity<?> cadastrarGato(@RequestBody @Valid GatoCreateDto gatoDto) {
        Ong ong = ongRepository.findById(gatoDto.getOngId())
                .orElseThrow(() -> new RuntimeException("ONG não encontrada"));
        Animal animal;
        Gato gato = new Gato(gatoDto);
        gato.setOng(ong);
        gato.calcularTaxaAdocao();
        animal = gato;
        animalRepository.save(animal);
        return ResponseEntity.ok("Animal cadastrado com sucesso.");
    }



}
