package com.example.adpotme_api.controller;

import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.ong.OngRepository;
import com.example.adpotme_api.ongUser.OngUser;
import com.example.adpotme_api.ongUser.OngUserCreateDto;
import com.example.adpotme_api.ongUser.OngUserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ongusers")
@Tag(name = "OngUser")
public class OngUserController {

    @Autowired
    private OngUserRepository ongUserRepository;

    @Autowired
    private OngRepository ongRepository;

    @PostMapping
    @Transactional
    public void createOngUser(@RequestBody @Valid OngUserCreateDto dto) {
        Ong ong = ongRepository.findById(dto.getOngId())
                .orElseThrow(() -> new RuntimeException("ONG não encontrada"));



        OngUser ongUser = new OngUser();
        ongUser.setNome(dto.getNome());
        ongUser.setCpf(dto.getCpf());
        ongUser.setFuncao(dto.getFuncao());
        ongUser.setOng(ong);

         ongUserRepository.save(ongUser);

    }
    @GetMapping
    public List<OngUser> recuperarOngUsers(){
        return ongUserRepository.findAll();
    }

    @GetMapping("/{id}")
    public OngUser recuperarOngUserPorId(@PathVariable Long id) {
        Optional<OngUser> ongUser = ongUserRepository.findById(id);
        if(ongUser.isPresent()) {
            return ongUser.get();
        }

        return null;
    }

    @PutMapping("/{id}")
    @Transactional
    public OngUser atualizar(@PathVariable Long id, @RequestBody OngUserCreateDto ongUserAtualizada) {

        OngUser ongUser = ongUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OngUser not found"));


        ongUser.setNome(ongUserAtualizada.getNome());
        ongUser.setCpf(ongUserAtualizada.getCpf());
        ongUser.setFuncao(ongUserAtualizada.getFuncao());

        // aqui to vendo se o id tá ong é valido e atualizando
        if (ongUserAtualizada.getOngId() != null) {
            Ong ong = ongRepository.findById(ongUserAtualizada.getOngId())
                    .orElseThrow(() -> new RuntimeException("Ong not found"));
            ongUser.setOng(ong);
        }


        return ongUserRepository.save(ongUser);
    }

    @DeleteMapping("/{id}")
    public void deletarOngUser(@PathVariable Long id){
        Optional<OngUser> optOngUser = ongUserRepository.findById(id);
        if(optOngUser.isPresent()){
            ongUserRepository.delete(optOngUser.get());
        }
    }




}
