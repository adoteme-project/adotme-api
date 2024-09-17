package com.example.adpotme_api.service;

import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.animal.Cachorro;
import com.example.adpotme_api.entity.animal.Gato;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.entity.ongUser.OngUser;
import com.example.adpotme_api.dto.ongUser.OngUserCreateDto;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.entity.requisicao.Status;
import com.example.adpotme_api.repository.AdotanteRepository;
import com.example.adpotme_api.repository.AnimalRepository;
import com.example.adpotme_api.repository.FormularioRepository;
import com.example.adpotme_api.repository.OngRepository;
import com.example.adpotme_api.repository.OngUserRepository;
import com.example.adpotme_api.repository.RequisicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public OngUser createOngUser(OngUserCreateDto dto) {
        Optional<Ong> ongOpt = ongRepository.findById(dto.getOngId());
        if (!ongOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ONG não encontrada");
        }

        Ong ong = ongOpt.get();
        OngUser ongUser = new OngUser();
        ongUser.setNome(dto.getNome());
        ongUser.setCpf(dto.getCpf());
        ongUser.setFuncao(dto.getFuncao());
        ongUser.setOng(ong);
        ongUser.setCadastro(dto.getCadastro());

        return ongUserRepository.save(ongUser);
    }

    public List<OngUser> findAllOngUsers() {
        return ongUserRepository.findAll();
    }

    public OngUser findOngUserById(Long id) {
        Optional<OngUser> ongUserOpt = ongUserRepository.findById(id);
        if (ongUserOpt.isPresent()) {
            return ongUserOpt.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG User não encontrado");
        }
    }

    public OngUser updateOngUser(Long id, OngUserCreateDto ongUserAtualizada) {
        Optional<OngUser> ongUserOpt = ongUserRepository.findById(id);
        if (!ongUserOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG User não encontrado");
        }

        OngUser ongUser = ongUserOpt.get();
        ongUser.setNome(ongUserAtualizada.getNome());
        ongUser.setCpf(ongUserAtualizada.getCpf());
        ongUser.setFuncao(ongUserAtualizada.getFuncao());

        if (ongUserAtualizada.getOngId() != null) {
            Optional<Ong> ongOpt = ongRepository.findById(ongUserAtualizada.getOngId());
            if (!ongOpt.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ONG não encontrada");
            }
            ongUser.setOng(ongOpt.get());
        }

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
        Optional<Adotante> adotanteOpt = adotanteRepository.findById(idAdotante);
        if (!adotanteOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adotante não encontrado");
        }

        Adotante adotante = adotanteOpt.get();
        Optional<Animal> animalOpt = animalRepository.findById(idAnimal);
        if (!animalOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal não encontrado");
        }

        Animal animal = animalOpt.get();
        if (animal instanceof Cachorro || animal instanceof Gato) {
            List<Formulario> formularios = formularioRepository.findByAdotanteId(adotante.getId());

            Formulario formularioEncontrado = null;
            for (Formulario formulario : formularios) {
                if (formulario.getAnimal().equals(animal)) {
                    formularioEncontrado = formulario;
                    break;
                }
            }

            if (formularioEncontrado != null) {
                Requisicao requisicao = formularioEncontrado.getRequisicao();
                requisicao.setStatus(Status.ADOTADO);

                animal.setIsAdotado(true);
                adotante.adotarAnimal(animal);

                adotanteRepository.save(adotante);
                return adotante;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formulário não encontrado para o animal");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Animal não pode ser adotado");
        }
    }

}
