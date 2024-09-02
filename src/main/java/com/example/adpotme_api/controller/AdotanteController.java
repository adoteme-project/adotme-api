package com.example.adpotme_api.controller;

import com.example.adpotme_api.adotante.Adotante;
import com.example.adpotme_api.adotante.AdotanteCreateDto;
import com.example.adpotme_api.adotante.AdotanteRepository;
import com.example.adpotme_api.animal.AnimalRepository;
import com.example.adpotme_api.animal.Cachorro;
import com.example.adpotme_api.animal.Gato;
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
    @Autowired
    private AnimalRepository animalRepository;

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

    @PutMapping("adotarCachorro/{id}/{idAnimal}")
    @Transactional
    public Adotante adotarCachorro(@PathVariable Long id, @PathVariable Long idAnimal) {
        Adotante adotante = adotanteRepository.findById(id).orElse(null);


            if(animalRepository.existsById(idAnimal)){
                Cachorro cachorro = (Cachorro) animalRepository.findById(idAnimal).get();

                cachorro.setIsAdotado(true);
                adotante.adotarAnimal(cachorro);
            }

            return adotanteRepository.save(adotante);





    }

    @PutMapping("adotarGato/{id}/{idAnimal}")
    @Transactional
    public Adotante adotarGato(@PathVariable Long id, @PathVariable Long idAnimal) {
        Adotante adotante = adotanteRepository.findById(id).orElse(null);


        if(animalRepository.existsById(idAnimal)){
            Gato gato = (Gato) animalRepository.findById(idAnimal).get();

            gato.setIsAdotado(true);
            adotante.adotarAnimal(gato);
        }

        return adotanteRepository.save(adotante);





    }
}
