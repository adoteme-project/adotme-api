package com.example.adpotme_api.controller;

import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.ong.OngRepository;
import com.example.adpotme_api.ongUser.OngUser;
import com.example.adpotme_api.ongUser.OngUserCreateDto;
import com.example.adpotme_api.ongUser.OngUserRepository;
import com.example.adpotme_api.requisicao.Requisicao;
import com.example.adpotme_api.requisicao.RequisicaoRepository;
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

    @Autowired
    private RequisicaoRepository requisicaoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<OngUser> createOngUser(@RequestBody @Valid OngUserCreateDto dto) {
        Ong ong = ongRepository.findById(dto.getOngId()).get();

        if(ong == null) {
            return ResponseEntity.notFound().build();
        }



        OngUser ongUser = new OngUser();
        ongUser.setNome(dto.getNome());
        ongUser.setCpf(dto.getCpf());
        ongUser.setFuncao(dto.getFuncao());
        ongUser.setOng(ong);

         ongUserRepository.save(ongUser);

         return ResponseEntity.status(201).body(ongUser);

    }
    @GetMapping
    public ResponseEntity<List<OngUser>> recuperarOngUsers(){
        if(ongUserRepository.findAll().isEmpty()) {
            return ResponseEntity.status(204).body(ongUserRepository.findAll());
        }

        return ResponseEntity.status(200).body(ongUserRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OngUser> recuperarOngUserPorId(@PathVariable Long id) {
        Optional<OngUser> ongUser = ongUserRepository.findById(id);
        if(ongUser.isPresent()) {
            return ResponseEntity.status(200).body(ongUser.get());
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<OngUser> atualizar(@PathVariable Long id, @RequestBody OngUserCreateDto ongUserAtualizada) {

       Optional<OngUser> optOngUser = ongUserRepository.findById(id);
        if(!optOngUser.isPresent()) {
            return ResponseEntity.status(404).build();
        }

        OngUser ongUser = optOngUser.get();

        ongUser.setNome(ongUserAtualizada.getNome());
        ongUser.setCpf(ongUserAtualizada.getCpf());
        ongUser.setFuncao(ongUserAtualizada.getFuncao());

        // aqui to vendo se o id tá ong é valido e atualizando
        if (ongUserAtualizada.getOngId() != null) {
            Ong ong = ongRepository.findById(ongUserAtualizada.getOngId())
                    .orElseThrow(() -> new RuntimeException("Ong not found"));
            ongUser.setOng(ong);
        }


        return ResponseEntity.status(200).body(ongUserRepository.save(ongUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOngUser(@PathVariable Long id){
        Optional<OngUser> optOngUser = ongUserRepository.findById(id);
        if(optOngUser.isPresent()){
            ongUserRepository.delete(optOngUser.get());
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }

    @PostMapping("/{idOngUser}/{idRequisicao}")
    public ResponseEntity<Void> iniciarRevisao(@PathVariable Long idOngUser, @PathVariable Long idRequisicao) {
        Optional<OngUser> optOngUser = ongUserRepository.findById(idOngUser);
        Optional<Requisicao> optRequisicao = requisicaoRepository.findById(idRequisicao);
        if(optOngUser.isPresent() && optRequisicao.isPresent()){
            OngUser ongUser = optOngUser.get();
            Requisicao requisicao = optRequisicao.get();


            requisicao.adicionarResponsavel(ongUser);

            ongUserRepository.save(ongUser);
            requisicaoRepository.save(requisicao);

            return ResponseEntity.status(201).build();
        }

        return ResponseEntity.status(400).build();

    }





}
