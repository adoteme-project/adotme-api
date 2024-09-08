package com.example.adpotme_api.controller;

import com.example.adpotme_api.animal.*;
import com.example.adpotme_api.animalPerdido.*;
import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.ong.OngRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animais-perdidos")
public class AnimalPerdidoController {

    @Autowired
    private AnimalPerdidoRepository animalPerdidoRepository;

    @Autowired
    private OngRepository ongRepository;

    @PostMapping("/cachorro")
    @Transactional
    public ResponseEntity<AnimalPerdido> cadastrarCachorroPerdido(@RequestBody @Valid CachorroPerdidoCreateDto cachorroDto) {
        Ong ong = ongRepository.findById(cachorroDto.getOngId())
                .orElseThrow(() -> new RuntimeException("ONG não encontrada"));
        AnimalPerdido animal;


        CachorroPerdido cachorro = new CachorroPerdido(cachorroDto);
        cachorro.setOng(ong);
        animal = cachorro;
        animalPerdidoRepository.save(animal);
        return ResponseEntity.status(201).body(cachorro);
    }
    @PostMapping("/gato")
    @Transactional
    public ResponseEntity<AnimalPerdido> cadastrarGato(@RequestBody @Valid GatoPerdidoCreateDto gatoDto) {
        Ong ong = ongRepository.findById(gatoDto.getOngId())
                .orElseThrow(() -> new RuntimeException("ONG não encontrada"));
        AnimalPerdido animal;
        GatoPerdido gato = new GatoPerdido(gatoDto);
        gato.setOng(ong);

        animal = gato;
        animalPerdidoRepository.save(animal);
        return ResponseEntity.status(201).body(gato);
    }
    @GetMapping
    public ResponseEntity<List<AnimalPerdido>> recuperarAnimais(){
        if(animalPerdidoRepository.findAll().isEmpty()) {
            return ResponseEntity.status(204).body(animalPerdidoRepository.findAll());
        }

        return ResponseEntity.status(200).body(animalPerdidoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalPerdido> recuperarAnimalPorId(@PathVariable Long id) {
        Optional<AnimalPerdido> optionalAnimal = animalPerdidoRepository.findById(id);

        if(optionalAnimal.isPresent()){
            AnimalPerdido animalEncontrado = optionalAnimal.get();
            return ResponseEntity.status(200).body(animalEncontrado) ;
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("cachorro/{id}")
    @Transactional
    public ResponseEntity<CachorroPerdido> atualizarCachorro(@PathVariable Long id, @RequestBody CachorroPerdidoCreateDto cachorroAtualizado) {

        if(!animalPerdidoRepository.findById(id).isPresent()){
            return ResponseEntity.status(404).build();
        }
        if(ongRepository.findById(cachorroAtualizado.getOngId()).isEmpty()){
            return ResponseEntity.status(404).build();
        }

        CachorroPerdido cachorro = (CachorroPerdido) animalPerdidoRepository.findById(id).get();


        cachorro.setApelido(cachorroAtualizado.getApelido());
        cachorro.setSexo(cachorroAtualizado.getSexo());
        cachorro.setEspecie(cachorroAtualizado.getEspecie());
        cachorro.setIsEncontrado(cachorroAtualizado.getIsEncontrado());
        cachorro.setDescricao(cachorroAtualizado.getDescricao());
        cachorro.setIsVisible(cachorroAtualizado.getIsVisible());
        cachorro.setPorte(cachorroAtualizado.getPorte());



        // aqui to vendo se o id tá ong é valido e atualizando
        if (cachorroAtualizado.getOngId() != null) {
            Ong ong = ongRepository.findById(cachorroAtualizado.getOngId())
                    .orElseThrow(() -> new RuntimeException("Ong not found"));
            cachorro.setOng(ong);
        }


        return ResponseEntity.status(201).body(animalPerdidoRepository.save(cachorro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAnimal(@PathVariable Long id) {
        if (animalPerdidoRepository.existsById(id)) {
            animalPerdidoRepository.deleteById(id);
            return ResponseEntity.status(204).build();

        }
        return ResponseEntity.status(404).build();
    }
}
