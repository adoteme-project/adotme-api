package com.example.adpotme_api.controller;

import com.example.adpotme_api.adotante.Adotante;
import com.example.adpotme_api.animal.*;
import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.ong.OngRepository;
import com.example.adpotme_api.ongUser.OngUser;
import com.example.adpotme_api.ongUser.OngUserCreateDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/animais")
@Tag(name = "Animal")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private OngRepository ongRepository;

    @PostMapping("/cachorro")
    @Transactional
    public ResponseEntity<Animal> cadastrarCachorro(@RequestBody @Valid CachorroCreateDto cachorroDto) {
        Ong ong = ongRepository.findById(cachorroDto.getOngId())
                .orElseThrow(() -> new RuntimeException("ONG não encontrada"));
                Animal animal;


                Cachorro cachorro = new Cachorro(cachorroDto);
                cachorro.setOng(ong);
                cachorro.calcularTaxaAdocao();
                animal = cachorro;
        animalRepository.save(animal);
        return ResponseEntity.status(201).body(cachorro);
    }
    @PostMapping("/gato")
    @Transactional
    public ResponseEntity<Animal> cadastrarGato(@RequestBody @Valid GatoCreateDto gatoDto) {
        Ong ong = ongRepository.findById(gatoDto.getOngId())
                .orElseThrow(() -> new RuntimeException("ONG não encontrada"));
        Animal animal;
        Gato gato = new Gato(gatoDto);
        gato.setOng(ong);
        gato.calcularTaxaAdocao();
        animal = gato;
        animalRepository.save(animal);
        return ResponseEntity.status(201).body(gato);
    }
    @GetMapping
    public ResponseEntity<List<Animal>> recuperarAnimais(){
        if(animalRepository.findAll().isEmpty()) {
            return ResponseEntity.status(204).body(animalRepository.findAll());
        }

        return ResponseEntity.status(200).body(animalRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> recuperarAnimalPorId(@PathVariable Long id) {
        Optional<Animal> optionalAnimal = animalRepository.findById(id);

        if(optionalAnimal.isPresent()){
            Animal animalEncontrado = optionalAnimal.get();
            return ResponseEntity.status(200).body(animalEncontrado) ;
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("cachorro/{id}")
    @Transactional
    public ResponseEntity<Cachorro> atualizarCachorro(@PathVariable Long id, @RequestBody CachorroCreateDto cachorroAtualizado) {

        if(!animalRepository.findById(id).isPresent()){
            return ResponseEntity.status(404).build();
        }
        if(ongRepository.findById(cachorroAtualizado.getOngId()).isEmpty()){
            return ResponseEntity.status(404).build();
        }

        Cachorro cachorro = (Cachorro) animalRepository.findById(id).get();


        cachorro.setNome(cachorroAtualizado.getNome());
        cachorro.setAnoNascimento(cachorroAtualizado.getAnoNascimento());
        cachorro.setSexo(cachorroAtualizado.getSexo());
        cachorro.setDataAbrigo(cachorroAtualizado.getDataAbrigo());
        cachorro.setEspecie(cachorroAtualizado.getEspecie());
        cachorro.setRaca(cachorroAtualizado.getRaca());
        cachorro.setIsCastrado(cachorroAtualizado.getIsCastrado());
        cachorro.setDescricao(cachorroAtualizado.getDescricao());
        cachorro.setIsVisible(cachorroAtualizado.getIsVisible());
        cachorro.setIsAdotado(cachorroAtualizado.getIsAdotado());
        cachorro.setIsDestaque(cachorroAtualizado.getIsDestaque());
        cachorro.setPorte(cachorroAtualizado.getPorte());
        cachorro.setIsVermifugado(cachorroAtualizado.getIsVermifugado());

        if(!(cachorroAtualizado.getTaxaAdocao() == null)) {
            cachorro.setTaxaAdocao(cachorroAtualizado.getTaxaAdocao());

        }
        else{
            cachorro.calcularTaxaAdocao();
            }

        // aqui to vendo se o id tá ong é valido e atualizando
        if (cachorroAtualizado.getOngId() != null) {
            Ong ong = ongRepository.findById(cachorroAtualizado.getOngId())
                    .orElseThrow(() -> new RuntimeException("Ong not found"));
            cachorro.setOng(ong);
        }


        return ResponseEntity.status(200).body(animalRepository.save(cachorro));
    }

    @PutMapping("gato/{id}")
    @Transactional
    public ResponseEntity<Gato> atualizarGato(@PathVariable Long id, @RequestBody GatoCreateDto gatoAtualizado) {

        if(!animalRepository.findById(id).isPresent()){
            return ResponseEntity.status(404).build();
        }
        if(ongRepository.findById(gatoAtualizado.getOngId()).isEmpty()){
            return ResponseEntity.status(404).build();
        }

        Gato gato = (Gato) animalRepository.findById(id).get();


        gato.setNome(gatoAtualizado.getNome());
        gato.setAnoNascimento(gatoAtualizado.getAnoNascimento());
        gato.setDataAbrigo(gatoAtualizado.getDataAbrigo());
        gato.setSexo(gatoAtualizado.getSexo());
        gato.setEspecie(gatoAtualizado.getEspecie());
        gato.setRaca(gatoAtualizado.getRaca());
        gato.setIsDestaque(gatoAtualizado.getIsDestaque());
        gato.setIsCastrado(gatoAtualizado.getIsCastrado());
        gato.setDescricao(gatoAtualizado.getDescricao());
        gato.setIsVisible(gatoAtualizado.getIsVisible());
        gato.setIsAdotado(gatoAtualizado.getIsAdotado());
        gato.setIsVermifugado(gatoAtualizado.getIsVermifugado());
        if(!(gatoAtualizado.getTaxaAdocao() == null)) {
            gato.setTaxaAdocao(gatoAtualizado.getTaxaAdocao());

        }
        else{
            gato.calcularTaxaAdocao();
        }


        // aqui to vendo se o id tá ong é valido e atualizando
        if (gatoAtualizado.getOngId() != null) {
            Ong ong = ongRepository.findById(gatoAtualizado.getOngId())
                    .orElseThrow(() -> new RuntimeException("Ong not found"));
            gato.setOng(ong);
        }


        return ResponseEntity.status(200).body(animalRepository.save(gato));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAnimal(@PathVariable Long id) {
        if (animalRepository.existsById(id)) {
            animalRepository.deleteById(id);
            return ResponseEntity.status(204).build();

        }
        return ResponseEntity.status(404).build();
    }


}
