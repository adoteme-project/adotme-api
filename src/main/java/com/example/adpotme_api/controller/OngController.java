package com.example.adpotme_api.controller;

import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.ong.OngCreateDto;
import com.example.adpotme_api.ong.OngRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void cadastrarOng(@RequestBody @Valid OngCreateDto dados){
        ongRepository.save(new Ong(dados));
    }
    @GetMapping
    public List<Ong> recuperarOngs(){
        return ongRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ong recuperarOngPorId(@PathVariable Long id){
        Optional<Ong> optOng = ongRepository.findById(id);

        if(optOng.isPresent()){
            return optOng.get();
        }

        return null;
    }

    @PutMapping("/{id}")
    @Transactional
    public Ong atualizar(@PathVariable Long id,
                            @RequestBody Ong ongAtualizada) {
        if (ongRepository.existsById(id)) {
            ongAtualizada.setId(id);
            ongAtualizada.setOngUser(ongRepository.findById(id).get().getOngUser());
            return ongRepository.save(ongAtualizada);

        }

        return null;

    }

    @DeleteMapping("/{id}")
    public void deletarOng(@PathVariable Long id){
        Optional<Ong> optOng = ongRepository.findById(id);
        if(optOng.isPresent()){
            ongRepository.delete(optOng.get());
        }
    }
}
