package com.example.adpotme_api.service;

import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.entity.endereco.ViaCepService;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.dto.ong.OngCreateDto;
import com.example.adpotme_api.repository.EnderecoRepository;
import com.example.adpotme_api.repository.OngRepository;
import com.example.adpotme_api.util.Sorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OngService {

    @Autowired
    private OngRepository ongRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Transactional
    public Ong cadastrarOng(OngCreateDto dados) {
        Endereco endereco = viaCepService.obterEnderecoPorCep(dados.getCep());
        enderecoRepository.save(endereco);

        Ong ong = new Ong(dados);
        ong.setEndereco(endereco);

        return ongRepository.save(ong);
    }

    public List<Ong> recuperarOngs() {
        return ongRepository.findAll();
    }

    public Ong recuperarOngPorId(Long id) {
        Optional<Ong> optOng = ongRepository.findById(id);
        if (optOng.isPresent()) {
            return optOng.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }
    }

    public List<Ong> recuperarOngsOrdenadoPorEstado() {
        List<Ong> ongs = ongRepository.findAll();
        Sorting.selectionSortOngByEstado(ongs);
        return ongs;
    }

    @Transactional
    public Ong atualizar(Long id, OngCreateDto ongAtualizada) {
        Optional<Ong> optOng = ongRepository.findById(id);
        if (optOng.isPresent()) {
            Ong ongAtt = new Ong(ongAtualizada);
            ongAtt.setId(id);
            ongAtt.setOngUser(optOng.get().getOngUser());
            return ongRepository.save(ongAtt);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada para atualização");
        }
    }

    public boolean deletarOng(Long id) {
        Optional<Ong> optOng = ongRepository.findById(id);
        if (optOng.isPresent()) {
            ongRepository.delete(optOng.get());
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada para deleção");
        }
    }
}
