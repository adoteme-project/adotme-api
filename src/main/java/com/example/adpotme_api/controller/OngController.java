package com.example.adpotme_api.controller;

import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.ong.OngCreateDto;
import com.example.adpotme_api.ong.OngRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("ongs")
@Tag(name = "Ong")
public class OngController {


    @Autowired
    private OngRepository ongRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Ong> cadastrarOng(@RequestBody @Valid OngCreateDto dados){
        return ResponseEntity.status(201).body(ongRepository.save(new Ong(dados)));
    }

    @GetMapping
    public ResponseEntity<List<Ong>> recuperarOngs(){
        if(ongRepository.findAll().isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ongRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ong> recuperarOngPorId(@PathVariable Long id){
        Optional<Ong> optOng = ongRepository.findById(id);

        if(optOng.isPresent()){
            return ResponseEntity.status(200).body(optOng.get());
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Ong> atualizar(@PathVariable Long id,
                            @RequestBody Ong ongAtualizada) {
        if (ongRepository.existsById(id)) {
            ongAtualizada.setId(id);
            ongAtualizada.setOngUser(ongRepository.findById(id).get().getOngUser());
            return ResponseEntity.status(200).body(ongRepository.save(ongAtualizada));

        }

        return ResponseEntity.status(404).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOng(@PathVariable Long id){
        Optional<Ong> optOng = ongRepository.findById(id);
        if(optOng.isPresent()){ongRepository.delete(optOng.get());
           return ResponseEntity.status(204).build() ;
        }
        return ResponseEntity.status(404).build();
    }
}
