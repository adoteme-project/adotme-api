package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.ong.*;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.dadosBancarios.DadosBancarios;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.entity.endereco.ViaCepService;
import com.example.adpotme_api.entity.formulario.Formulario;
import com.example.adpotme_api.entity.image.Image;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.entity.requisicao.Requisicao;
import com.example.adpotme_api.entity.requisicao.Status;
import com.example.adpotme_api.integration.CloudinaryService;
import com.example.adpotme_api.mapper.OngMapper;
import com.example.adpotme_api.repository.*;
import com.example.adpotme_api.util.PesquisaBinaria;
import com.example.adpotme_api.util.Sorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    @Autowired
    private CloudinaryService cloudinaryService;


    @Autowired
    private DadosBancariosRepository dadosBancariosRepository;
    @Autowired
    private RequisicaoRepository requisicaoRepository;


    @Transactional
    public OngResponseAllDto cadastrarOng(OngCreateDto dados, String numero, MultipartFile qrCode) {
        Endereco endereco = viaCepService.obterEnderecoPorCep(dados.getCep());
        endereco.setNumero(numero);
        enderecoRepository.save(endereco);


        Ong ong = OngMapper.toEntity(dados);
        DadosBancarios dadosBancarios = new DadosBancarios();
        dadosBancarios.setBanco(dados.getDadosBancarios().getBanco());
        dadosBancarios.setAgencia(dados.getDadosBancarios().getAgencia());
        dadosBancarios.setConta(dados.getDadosBancarios().getConta());
        dadosBancarios.setTipoConta(dados.getDadosBancarios().getTipoConta());
        dadosBancarios.setChavePix(dados.getDadosBancarios().getChavePix());
        dadosBancarios.setNomeTitular(dados.getDadosBancarios().getNomeTitular());
        dadosBancarios.setOng(ong);
        ong.setDadosBancarios(dadosBancarios);
        dadosBancariosRepository.save(dadosBancarios);




        if (qrCode != null) {
            try {
                Image qrCodeImage = cloudinaryService.upload(qrCode);
                dadosBancarios.setQrCode(qrCodeImage);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao salvar imagem");
            }
        }


        ong.setEndereco(endereco);
        ongRepository.save(ong);

        return OngMapper.toOngResponseAll(ong);
    }

    public List<OngResponseDto> recuperarOngs() {
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

    public List<OngResponseAllDto> recuperarOngsComDadosBancarios() {
        List<Ong> ongs = ongRepository.findAll();
        List<OngResponseAllDto> ongsDto = new ArrayList<>();

        for (Ong ong : ongs) {
            OngResponseAllDto ongVez = OngMapper.toOngResponseAll(ong);
            ongsDto.add(ongVez);
        }

        return ongsDto;
    }

    public OngResponseAllDto pesquisarOngPorNome(String nome) {
        List<Ong> ongs = ongRepository.findAll();
        Sorting.quickSortPorNome(ongs);
        OngResponseAllDto[] ongResponseAllDtos = new OngResponseAllDto[ongs.size()];
        for (int i = 0; i < ongs.size(); i++) {
            Ong ong = ongs.get(i);
            OngResponseAllDto ongResponse = OngMapper.toOngResponseAll(ong);
            ongResponseAllDtos[i] = ongResponse;

        }
        int indice = PesquisaBinaria.pesquisaBinaria(ongResponseAllDtos, nome);
        if (indice == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }
        return ongResponseAllDtos[indice];
    }

    public List<OngAnimaisDto> listagemAnimaisOng(Long id) {
        Optional<Ong> optOng = ongRepository.findById(id);
        if (optOng.isPresent()) {
            Ong ong = optOng.get();
            List<OngAnimaisDto> animais = new ArrayList<>();
            List<Animal> animaisOng = ong.getAnimal();
            for(Animal animalOng : animaisOng) {
                OngAnimaisDto animal = OngMapper.toOngAnimal(animalOng);

                List<Requisicao> requisicoes = requisicaoRepository.findByAnimal(animalOng);

                if(requisicoes.isEmpty()){
                    animal.setSituacao("Sem aplicação");
                }
                else{
                for(Requisicao requisicao : requisicoes) {
                    if (requisicao.getStatus() == Status.REPROVADO) {
                        animal.setSituacao("Sem aplicação");
                    } else if (requisicao.getStatus() == Status.REVISAO || requisicao.getStatus() == Status.INICIO_DA_APLICACAO) {
                        animal.setSituacao("Revisão");
                    } else if (requisicao.getStatus() == Status.DOCUMENTACAO) {
                        animal.setSituacao("Documentação");
                    } else if (requisicao.getStatus() == Status.APROVADO) {
                        animal.setSituacao("Aprovado");
                    } else if (requisicao.getStatus() == Status.ADOTADO) {
                        animal.setSituacao("Adotado");
                    }
                }

                }

            animais.add(animal);
            }
            return animais;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ONG não encontrada");
        }
    }
}
