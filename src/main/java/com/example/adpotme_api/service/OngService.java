package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.ong.OngResponseDto;
import com.example.adpotme_api.dto.ong.OngUpdateDto;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.entity.endereco.ViaCepService;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.dto.ong.OngCreateDto;
import com.example.adpotme_api.mapper.OngMapper;
import com.example.adpotme_api.repository.EnderecoRepository;
import com.example.adpotme_api.repository.OngRepository;
import com.example.adpotme_api.util.Sorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.ArrayList;
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
    public Ong cadastrarOng(OngCreateDto dados, String numero) {
        Endereco endereco = viaCepService.obterEnderecoPorCep(dados.getCep());
        endereco.setNumero(numero);
        enderecoRepository.save(endereco);

        Ong ong = new Ong(dados);
        ong.setEndereco(endereco);

        return ongRepository.save(ong);
    }

    public List<OngResponseDto> recuperarOngs() throws IOException {
        List<Ong> ongs = ongRepository.findAll();

        List<OngResponseDto> ongsDto = new ArrayList<>();

        for (Ong ong : ongs) {
          OngResponseDto ongVez =  OngMapper.toOngResponseDto(ong);
            ongsDto.add(ongVez);
        }

        return ongsDto;

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
    public Ong atualizar(Long id, OngUpdateDto ongAtualizada) {
        Optional<Ong> optOng = ongRepository.findById(id);
        if (optOng.isPresent()) {
            Ong ongAtt = optOng.get();

            ongAtt.setNome(ongAtualizada.getNome());
            ongAtt.setCnpj(ongAtualizada.getCnpj());
            ongAtt.setEmail(ongAtualizada.getEmail());
            ongAtt.setTelefone(ongAtualizada.getTelefone());

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
