package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.adotante.*;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.entity.endereco.ViaCepService;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.entity.image.Image;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.mapper.AdotanteMapper;
import com.example.adpotme_api.mapper.AnimalMapper;
import com.example.adpotme_api.mapper.FormularioMapper;
import com.example.adpotme_api.repository.*;
import com.example.adpotme_api.integration.CloudinaryService;
import com.example.adpotme_api.util.Sorting;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdotanteService {

    @Autowired
    private AdotanteRepository adotanteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private OngRepository ongRepository;

    @Autowired
    private ViaCepService viaCepService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    private FormularioRepository formularioRepository;
    @Autowired
    private OngUserRepository ongUserRepository;
    @Autowired
    private Validator validator;
    @Autowired
    private AnimalRepository animalRepository;


    @Transactional
    public AdotanteResponseDto cadastrarAdotante(AdotanteCreateDto dados, MultipartFile fotoPerfil) {
        Set<ConstraintViolation<AdotanteCreateDto>> violations = validator.validate(dados);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<AdotanteCreateDto> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, sb.toString());
        }
        Endereco endereco = viaCepService.obterEnderecoPorCep(dados.getCep());
        endereco.setNumero(dados.getNumero());
        enderecoRepository.save(endereco);

        if(ongUserRepository.existsByEmail(dados.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
        }


        Adotante adotante = new Adotante();
        adotante.setNome(dados.getNome());
        adotante.setDtNasc(dados.getDtNasc());
        adotante.setEmail(dados.getEmail());
        adotante.setCelular(dados.getCelular());
        adotante.setEndereco(endereco);
        adotante.setCadastro(dados.getCadastro());

        if(fotoPerfil != null && !fotoPerfil.isEmpty()) {
            try {
                Image image = cloudinaryService.upload(fotoPerfil);
                adotante.setFotoPerfil(image);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        adotante.setSenha(passwordEncoder.encode(dados.getSenha()));

        Formulario formulario = FormularioMapper.toFormulario(dados);
        adotante.setFormulario(formulario);
        formulario.setAdotante(adotante);

        formularioRepository.save(formulario);
        adotanteRepository.save(adotante);


        return AdotanteMapper.toResponseDto(adotante);
    }

    public List<AdotanteResponseDto> recuperarAdotantes() {
        List<Adotante> adotantes =  adotanteRepository.findAll();
        List<AdotanteResponseDto> adotantesResponse = new ArrayList<>();
        for (Adotante adotante : adotantes) {
           AdotanteResponseDto adotanteDaVez =  AdotanteMapper.toResponseDto(adotante);
            adotantesResponse.add(adotanteDaVez);

        }
        return adotantesResponse;
    }

    public Adotante recuperarAdotantePorId(Long id) {
        Optional<Adotante> adotanteOpt = adotanteRepository.findById(id);
        if (adotanteOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }
        return adotanteOpt.get();
    }

    public List<AdotanteResponseDto> recuperarAdotantesOrdenadosPorEstado() {
        List<Adotante> adotantes = adotanteRepository.findAll();
        List<AdotanteResponseDto> adotantesResponse = new ArrayList<>();

        for(Adotante adotante : adotantes){
            AdotanteResponseDto adotanteResponse = AdotanteMapper.toResponseDto(adotante);
            adotantesResponse.add(adotanteResponse);
        }

        Sorting.quickSortAdotante(adotantesResponse);
        return adotantesResponse;
    }

    @Transactional
    public Adotante atualizarAdotante(Long id, AdotanteUpdateDto adotante) {
        Optional<Adotante> adotanteOpt = adotanteRepository.findById(id);
        if(adotanteOpt.isPresent()){
            Adotante adotanteAtualizado = adotanteOpt.get();
            adotanteAtualizado.setNome(adotante.getNome());
            adotanteAtualizado.setEmail(adotante.getEmail());
            adotanteAtualizado.setSenha(adotante.getSenha());
            adotanteAtualizado.setDtNasc(adotante.getDtNasc());
            adotanteAtualizado.setCelular(adotante.getCelular());

            return adotanteRepository.save(adotanteAtualizado);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");

    }

    @Transactional
    public void deletarAdotante(Long id) {
        if (!adotanteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }
        adotanteRepository.deleteById(id);
    }

    public AdotanteDadosFoto recuperarAdotanteDadosFoto(Long id) {
        Optional<Adotante> adotanteOpt = adotanteRepository.findById(id);
        if(adotanteOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }
        Adotante adotante = adotanteOpt.get();
        return AdotanteMapper.toAdotanteDadosFoto(adotante);
    }

    public AnimalFavoritoUsuarioDto recuperarAnimaisFavoritosAdotante(Long id) {
        Optional<Adotante> adotanteOpt = adotanteRepository.findById(id);
        if(adotanteOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }
        Adotante adotante = adotanteOpt.get();

          return AdotanteMapper.toAnimalFavoritoUsuarioDto(adotante);

    }

    public Adotante favoritarAnimal(Long idAdotante, Long idAnimal) {
        Optional<Adotante> adotanteOpt = adotanteRepository.findById(idAdotante);
        if(adotanteOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }
        Adotante adotante = adotanteOpt.get();

        Optional<Animal> animalOpt = animalRepository.findById(idAnimal);
        if(animalOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }
        for(Animal animal : adotante.getFavoritos()){
            if(animal.getId().equals(idAnimal)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Animal já favoritado");
            }
        }
        Animal animal = animalOpt.get();
        adotante.getFavoritos().add(animal);
        return adotanteRepository.save(adotante);
    }

    public Adotante favoritarOng(Long idAdotante, Long idOng) {
        Optional<Adotante> adotanteOpt = adotanteRepository.findById(idAdotante);
        if(adotanteOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }
        Adotante adotante = adotanteOpt.get();

        Optional<Ong> ongOpt = ongRepository.findById(idOng);
        if(ongOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ong não encontrada");
        }
        for(Ong ong : adotante.getOngFavoritas()){
            if(ong.getId().equals(idOng)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ong já favoritada");
            }
        }
        Ong ong = ongOpt.get();
        adotante.getOngFavoritas().add(ong);
        return adotanteRepository.save(adotante);
    }

    public AdotanteFavoritoOngDto recuperarOngsFavoritasAdotante(Long id) {
        Optional<Adotante> adotanteOpt = adotanteRepository.findById(id);
        if(adotanteOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }
        Adotante adotante = adotanteOpt.get();
        return AdotanteMapper.toAdotanteFavoritoOngDto(adotante);
    }
}
