package com.example.adpotme_api.controller;

import com.example.adpotme_api.adotante.Adotante;
import com.example.adpotme_api.adotante.AdotanteCreateDto;
import com.example.adpotme_api.adotante.AdotanteRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adotantes")
@Tag(name = "Adotante")
public class AdotanteController {

    @Autowired
    private AdotanteRepository adotanteRepository;

    @PostMapping
    @Transactional
    public void cadastrarOng(@RequestBody @Valid AdotanteCreateDto dados){
        adotanteRepository.save(new Adotante(dados));
    }
    @GetMapping
    public List<Adotante> recuperarAdotante(){
        return adotanteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Adotante recuperarAdotantePorId(@PathVariable Long id){
        Optional<Adotante> optAdotante = adotanteRepository.findById(id);

        if(optAdotante.isPresent()){
            return optAdotante.get();
        }

        return null;
    }

    @PutMapping("/{id}")
    @Transactional
    public Adotante atualizar(@PathVariable Long id,
                         @RequestBody Adotante adotante) {
        if (adotanteRepository.existsById(id)) {
            adotante.setId(id);

            return adotanteRepository.save(adotante);

        }

        return null;

    }

    @DeleteMapping("/{id}")
    public void deletarAdotante(@PathVariable Long id){
        Optional<Adotante> optAdotante = adotanteRepository.findById(id);
        if(optAdotante.isPresent()){
            adotanteRepository.delete(optAdotante.get());
        }
    }
}
