package com.example.adpotme_api.controller;

import com.example.adpotme_api.adotante.Adotante;
import com.example.adpotme_api.adotante.AdotanteRepository;
import com.example.adpotme_api.animal.AnimalRepository;
import com.example.adpotme_api.animal.Cachorro;
import com.example.adpotme_api.animal.Gato;
import com.example.adpotme_api.formulario.Formulario;
import com.example.adpotme_api.formulario.FormularioRepository;
import com.example.adpotme_api.ong.Ong;
import com.example.adpotme_api.ong.OngRepository;
import com.example.adpotme_api.ongUser.OngUser;
import com.example.adpotme_api.ongUser.OngUserCreateDto;
import com.example.adpotme_api.ongUser.OngUserRepository;
import com.example.adpotme_api.requisicao.Requisicao;
import com.example.adpotme_api.requisicao.RequisicaoRepository;
import com.example.adpotme_api.requisicao.Status;
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
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private FormularioRepository formularioRepository;
    @Autowired
    private AdotanteRepository adotanteRepository;

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
        ongUser.setCadastro(dto.getCadastro());

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


    @PutMapping("/adocao-animal/{id}/{idAnimal}")
    @Transactional
    public ResponseEntity<Adotante> adotarAnimal(@PathVariable Long id, @PathVariable Long idAnimal) {
        Adotante adotante = adotanteRepository.findById(id).orElse(null);


        if(!animalRepository.existsById(idAnimal)){
            return ResponseEntity.status(404).build();
        }

        if(animalRepository.findById(idAnimal).get() instanceof Cachorro){
            Cachorro cachorro = (Cachorro) animalRepository.findById(idAnimal).get();
            Formulario formulario = new Formulario();
            List<Formulario> formularioPesquisa = formularioRepository.findByAdotanteId(adotante.getId());

            for (Formulario f : formularioPesquisa) {
                if(f.getAnimal() == cachorro){
                    formulario = f;
                }
            }
            Requisicao requisicao = formulario.getRequisicao();

            requisicao.setStatus(Status.ADOTADO);
            cachorro.setIsAdotado(true);
            adotante.adotarAnimal(cachorro);

        }
        else if(animalRepository.findById(idAnimal).get() instanceof Gato){
            Gato gato = (Gato) animalRepository.findById(idAnimal).get();
            Formulario formulario = new Formulario();
            List<Formulario> formularioPesquisa = formularioRepository.findByAdotanteId(adotante.getId());

            for (Formulario f : formularioPesquisa) {
                if(f.getAnimal() == gato){
                    formulario = f;
                }
            }
            Requisicao requisicao = formulario.getRequisicao();

            requisicao.setStatus(Status.ADOTADO);
            gato.setIsAdotado(true);
            adotante.adotarAnimal(gato);
        }

        return ResponseEntity.status(200).body(adotanteRepository.save(adotante));

    }


}
