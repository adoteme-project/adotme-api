package com.example.adpotme_api.controller;

import com.example.adpotme_api.adotante.Adotante;
import com.example.adpotme_api.adotante.AdotanteCreateDto;
import com.example.adpotme_api.adotante.AdotanteRepository;
import com.example.adpotme_api.animal.AnimalRepository;
import com.example.adpotme_api.animal.Cachorro;
import com.example.adpotme_api.animal.Gato;
import com.example.adpotme_api.formulario.Formulario;
import com.example.adpotme_api.formulario.FormularioRepository;
import com.example.adpotme_api.requisicao.Requisicao;
import com.example.adpotme_api.requisicao.RequisicaoRepository;
import com.example.adpotme_api.requisicao.Status;
import com.example.adpotme_api.requisicaoUser.RequisicaoUserResponsavel;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adotantes")
@Tag(name = "Adotante")
public class AdotanteController {

    @Autowired
    private AdotanteRepository adotanteRepository;
    @Autowired
    private AnimalRepository animalRepository;
    private RequisicaoRepository requisicaoRepository;
    @Autowired
    private FormularioRepository formularioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Adotante> cadastrarOng(@RequestBody @Valid AdotanteCreateDto dados){
        return ResponseEntity.status(201).body(adotanteRepository.save(new Adotante(dados)));

    }
    @GetMapping
    public ResponseEntity<List<Adotante>> recuperarAdotante(){
        if(adotanteRepository.findAll().isEmpty()) {
            return ResponseEntity.status(204).body(adotanteRepository.findAll());
        }

        return ResponseEntity.status(200).body(adotanteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Adotante> recuperarAdotantePorId(@PathVariable Long id){
        Optional<Adotante> optAdotante = adotanteRepository.findById(id);

        if(optAdotante.isPresent()){
            Adotante adotanteEncontrado = optAdotante.get();
            return ResponseEntity.status(200).body(adotanteEncontrado) ;
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Adotante> atualizar(@PathVariable Long id,
                         @RequestBody Adotante adotante) {
        if (adotanteRepository.existsById(id)) {
            adotante.setId(id);

            return ResponseEntity.status(200).body(adotanteRepository.save(adotante));

        }

        return ResponseEntity.status(404).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAdotante(@PathVariable Long id){
        Optional<Adotante> optAdotante = adotanteRepository.findById(id);
        if(optAdotante.isPresent()){
            adotanteRepository.delete(optAdotante.get());
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }



}
