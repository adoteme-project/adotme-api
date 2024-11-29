package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.animal.AnimalOngDto;
import com.example.adpotme_api.dto.animal.AnimalOngResponseDto;
import com.example.adpotme_api.dto.ong.*;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.ong.Ong;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OngMapper {
    public static OngResponseDto toOngResponseDto(Ong ong) {
        OngResponseDto ongResponseDto = new OngResponseDto();
        ongResponseDto.setId(ong.getId());
        ongResponseDto.setNome(ong.getNome());
        ongResponseDto.setEmail(ong.getEmail());
        ongResponseDto.setTelefone(ong.getTelefone());
        ongResponseDto.setCnpj(ong.getCnpj());
        ongResponseDto.setEndereco(EnderecoMapper.toEnderecoResponseOngDto(ong.getEndereco()));
        ongResponseDto.setAnimais(toAnimalOngResponseDto(ong.getAnimal()));
        return ongResponseDto;

    }

    public static Ong toEntity(OngCreateDto dto){
        Ong ong = new Ong();
        ong.setNome(dto.getNome());
        ong.setEmail(dto.getEmail());
        ong.setTelefone(dto.getTelefone());
        ong.setCnpj(dto.getCnpj());
        return ong;
    }

    public static OngResponseAllDto toOngResponseAll (Ong ong){
        OngResponseAllDto ongResponseAllDto = new OngResponseAllDto();
        ongResponseAllDto.setId(ong.getId());
        ongResponseAllDto.setNome(ong.getNome());
        ongResponseAllDto.setEmail(ong.getEmail());
        ongResponseAllDto.setTelefone(ong.getTelefone());
        ongResponseAllDto.setFotoUrl(ong.getImagem().getUrl());
        ongResponseAllDto.setCnpj(ong.getCnpj());
        ongResponseAllDto.setEndereco(EnderecoMapper.toEnderecoResponseOngDto(ong.getEndereco()));
        ongResponseAllDto.setDadosBancarios(DadosBancariosMapper.toDto(ong.getDadosBancarios()));
        return ongResponseAllDto;

    }

    public static List<AnimalOngResponseDto> toAnimalOngResponseDto(List<Animal> animais) {
        List<AnimalOngResponseDto> animaisResposta = new ArrayList<>();
        for (Animal animal : animais) {
            animaisResposta.add(AnimalMapper.toAnimalOngResponseDto(animal));
        }

        return animaisResposta;
    }

    public static OngAnimaisDto toOngAnimal(Animal animal){
        OngAnimaisDto ongAnimaisDto = new OngAnimaisDto();
        ongAnimaisDto.setId(animal.getId());
        ongAnimaisDto.setNome(animal.getNome());
        ongAnimaisDto.setEspecie(animal.getEspecie());
        ongAnimaisDto.setRaca(animal.getRaca());
        ongAnimaisDto.setTaxaAdocao(animal.getTaxaAdocao());
        ongAnimaisDto.setDataEntrada(animal.getDataAbrigo());
        ongAnimaisDto.setVisibilidade(animal.getIsVisible());
        return ongAnimaisDto;
    }

    public static OngDadosBancariosAnimalDto toOngDadosBancariosAnimal(Ong ong) {
        List<Animal> animais = ong.getAnimal();
        List<AnimalOngDto> animaisDto = new ArrayList<>();
        for (Animal animal : animais) {
            AnimalOngDto animalDto = AnimalMapper.toAnimalOngDto(animal);
            animaisDto.add(animalDto);
        }
        OngDadosBancariosAnimalDto ongDadosBancariosAnimalDto = new OngDadosBancariosAnimalDto();
        ongDadosBancariosAnimalDto.setId(ong.getId());
        ongDadosBancariosAnimalDto.setNome(ong.getNome());
        ongDadosBancariosAnimalDto.setEmail(ong.getEmail());
        ongDadosBancariosAnimalDto.setTelefone(ong.getTelefone());
        ongDadosBancariosAnimalDto.setCnpj(ong.getCnpj());
        ongDadosBancariosAnimalDto.setBanco(ong.getDadosBancarios().getBanco());
        ongDadosBancariosAnimalDto.setAgencia(ong.getDadosBancarios().getAgencia());
        ongDadosBancariosAnimalDto.setConta(ong.getDadosBancarios().getConta());
        ongDadosBancariosAnimalDto.setTipoConta(ong.getDadosBancarios().getTipoConta());
        ongDadosBancariosAnimalDto.setChavePix(ong.getDadosBancarios().getChavePix());
        ongDadosBancariosAnimalDto.setNomeTitular(ong.getDadosBancarios().getNomeTitular());
        ongDadosBancariosAnimalDto.setQrCode(ong.getDadosBancarios().getQrCode().getUrl());
        ongDadosBancariosAnimalDto.setEndereco(EnderecoMapper.toEnderecoResponseOngDto(ong.getEndereco()));
        ongDadosBancariosAnimalDto.setAnimais(animaisDto);
        return ongDadosBancariosAnimalDto;
    }

    public static OngFavoritaDto toOngFavoritaDto(Ong ong) {
        return OngFavoritaDto.builder()
                .ongId(ong.getId())
                .nome(ong.getNome())
                .imagem(ong.getImagem().getUrl())
                .endereco(EnderecoMapper.toEnderecoDto(ong.getEndereco()))
                .build();
    }

    public static OngPutViewDto toOngPutView(Ong ong) {
        return OngPutViewDto.builder()
                .nome(ong.getNome())
                .email(ong.getEmail())
                .celular(ong.getCelular())
                .site(ong.getSite())
                .instagram(ong.getInstagram())
                .facebook(ong.getFacebook())
                .telefone(ong.getTelefone())
                .descricao(ong.getDescricao())
                .imagemUrl(ong.getImagem().getUrl())
                .build();
    }
}
