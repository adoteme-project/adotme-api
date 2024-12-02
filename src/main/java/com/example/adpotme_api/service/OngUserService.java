package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.ongUser.OngUserAllDto;
import com.example.adpotme_api.dto.ongUser.OngUserEditDto;
import com.example.adpotme_api.dto.ongUser.OngUserUpdateDto;
import com.example.adpotme_api.entity.adocao.LogAdocao;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.entity.ongUser.OngUser;
import com.example.adpotme_api.dto.ongUser.OngUserCreateDto;
import com.example.adpotme_api.entity.ongUser.Role;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.mapper.OngUserMapper;
import com.example.adpotme_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OngUserService {

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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LogAdocaoRepository logAdocaoRepository;

    public OngUserEditDto createOngUser(OngUserCreateDto dto) {
        Optional<Ong> ongOpt = ongRepository.findById(dto.getOngId());
        if (!ongOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ONG não encontrada");
        }

        if(adotanteRepository.existsByEmail(dto.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
        }

        Ong ong = ongOpt.get();
        OngUser ongUser = new OngUser();
        ongUser.setNome(dto.getNome());
        ongUser.setRole(Role.valueOf(dto.getRole()));
        ongUser.setOng(ong);
        ongUser.setCelular(dto.getCelular());
        ongUser.setTelefone(dto.getTelefone());
        ongUser.setEmail(dto.getEmail());
        ongUser.setSenha(dto.getSenha());
        ongUser.setSenha(passwordEncoder.encode(dto.getSenha()));

        ongUserRepository.save(ongUser);

        return OngUserMapper.toOngUserEditDto(ongUser);


    }

    public List<OngUser> findAllOngUsers() {
        return ongUserRepository.findAll();
    }

    public OngUserEditDto findOngUserById(Long id) {
        Optional<OngUser> ongUserOpt = ongUserRepository.findById(id);
        if (!ongUserOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG User não encontrado");
        }

        OngUser ongUser = ongUserOpt.get();
        return OngUserMapper.toOngUserEditDto(ongUser);
    }

    public OngUser updateOngUser(Long id, OngUserUpdateDto ongUserAtualizada) {
        Optional<OngUser> ongUserOpt = ongUserRepository.findById(id);
        if (!ongUserOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG User não encontrado");
        }

        OngUser ongUser = ongUserOpt.get();
        ongUser.setNome(ongUserAtualizada.getNome());
        ongUser.setTelefone(ongUserAtualizada.getTelefone());
        ongUser.setCelular(ongUserAtualizada.getCelular());
        ongUser.setRole(Role.valueOf(ongUserAtualizada.getFuncao()));
        ongUser.setEmail(ongUserAtualizada.getEmail());
        ongUser.setSenha(ongUserAtualizada.getSenha());

        return ongUserRepository.save(ongUser);
    }

    public void deleteOngUser(Long id) {
        if (ongUserRepository.existsById(id)) {
            ongUserRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG User não encontrado");
        }
    }

    public void iniciarRevisao(Long idOngUser, Long idRequisicao) {
        Optional<OngUser> optOngUser = ongUserRepository.findById(idOngUser);
        Optional<Requisicao> optRequisicao = requisicaoRepository.findById(idRequisicao);

        if (optOngUser.isPresent() && optRequisicao.isPresent()) {
            OngUser ongUser = optOngUser.get();
            Requisicao requisicao = optRequisicao.get();

            requisicao.adicionarResponsavel(ongUser);
            ongUserRepository.save(ongUser);
            requisicaoRepository.save(requisicao);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ONG User ou Requisição não encontrada");
        }
    }

    public Adotante adotarAnimal(Long idAdotante, Long idAnimal) {
        Optional<Adotante> optAdotante = adotanteRepository.findById(idAdotante);
        Optional<Animal> optAnimal = animalRepository.findById(idAnimal);

        if (optAdotante.isPresent() && optAnimal.isPresent()) {
            Adotante adotante = optAdotante.get();
            Animal animal = optAnimal.get();

            adotante.adotarAnimal(animal);
            LogAdocao adocao = new LogAdocao();
            adocao.setNomeAdotante(adotante.getNome());
            adocao.setOngId(animal.getOng().getId());
            adocao.setNomeAnimal(animal.getNome());
            adocao.setData(LocalDate.now());
            adocao.setTipo("adoção");

            logAdocaoRepository.save(adocao);
            adotanteRepository.save(adotante);
            animalRepository.save(animal);

            return adotante;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adotante ou Animal não encontrado");
        }
    }


    public List<OngUserAllDto> findAllOngUsersByOng(Long ongId) {
        Ong ong = ongRepository.findById(ongId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada"));
        List<OngUser> ongUsers = ongUserRepository.findAllByOng(ong);
        if(ongUsers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nenhum usuário ONG encontrado");
        }

        List<OngUserAllDto> ongUserAllDtos = new ArrayList<>();

        for(OngUser ongUser : ongUsers) {
            OngUserAllDto userDto = OngUserMapper.toOngUserAllDto(ongUser);
            ongUserAllDtos.add(userDto);
        }

        return ongUserAllDtos;
    }
}
