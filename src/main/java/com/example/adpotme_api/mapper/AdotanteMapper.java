package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.adotante.*;
import com.example.adpotme_api.dto.animal.AnimalFavoritoDto;
import com.example.adpotme_api.dto.ong.OngFavoritaDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.entity.requisicao.Requisicao;

import java.util.ArrayList;
import java.util.List;

public class AdotanteMapper {

    public static AdotanteResponseDto toResponseDto(Adotante adotante) {
        return AdotanteResponseDto.builder()
                .id(adotante.getId())
                .nome(adotante.getNome())
                .dtNasc(adotante.getDtNasc())
                .cadastro(adotante.getCadastro())
                .email(adotante.getEmail())
                .senha(adotante.getSenha())
                .celular(adotante.getCelular())
                .endereco(adotante.getEndereco())
                .fotoPerfil(adotante.getFotoPerfil() != null ? adotante.getFotoPerfil().getUrl() : null)
                .formulario(adotante.getFormulario() != null ? FormularioMapper.toResponseDto(adotante.getFormulario()) : null)
                .build();
    }

    public static AdotanteUserDto toAdotanteAutenticadoDto(Adotante adotante) {
        if(adotante == null) {
            return null;
        }

        AdotanteUserDto dto = AdotanteUserDto
                .builder()
                .id(adotante.getId())
                .nome(adotante.getNome())
                .email(adotante.getEmail())
                .build();

        return dto;
    }

    public static AdotanteFormularioDto toAdontanteFormularioDto(Adotante adotante) {
        return AdotanteFormularioDto.builder()
                .nome(adotante.getNome())
                .email(adotante.getEmail())
                .dataNascimento(adotante.getDtNasc())
                .telefone(adotante.getCelular())
                .fotoPerfil(adotante.getFotoPerfil() != null ? adotante.getFotoPerfil().getUrl() : null)
                .endereco(adotante.getEndereco() != null ? EnderecoMapper.toEnderecoResponseOngDto(adotante.getEndereco()) : null)
                .formulario(adotante.getFormulario() != null ? FormularioMapper.toResponseDto(adotante.getFormulario()) : null)
                .build();
    }

    public static AdotanteDadosFoto toAdotanteDadosFoto(Adotante adotante) {
        return AdotanteDadosFoto.builder()
                .nome(adotante.getNome())
                .dataNascimeto(adotante.getDtNasc())
                .email(adotante.getEmail())
                .telefone(adotante.getCelular())
                .urlFoto(adotante.getFotoPerfil() != null ? adotante.getFotoPerfil().getUrl() : null)
                .endereco(adotante.getEndereco() != null ? EnderecoMapper.toEnderecoDto(adotante.getEndereco()) : null)
                .build();
    }

    public static AnimalFavoritoUsuarioDto toAnimalFavoritoUsuarioDto(Adotante adotante) {
        List<Animal> animaisAdotante = adotante.getFavoritos();
        List<AnimalFavoritoDto> animaisFavoritos = new ArrayList<>();
        for (Animal animal : animaisAdotante) {
            animaisFavoritos.add(AnimalMapper.toAnimalFavorito(animal));
        }

        return AnimalFavoritoUsuarioDto.builder()
                .usuarioId(adotante.getId())
                .animaisfavoritos(animaisFavoritos)
                .build();
    }
    public static AdotanteFavoritoOngDto toAdotanteFavoritoOngDto(Adotante adotante) {
        List<OngFavoritaDto> ongFavoritas = new ArrayList<>();
        for (Ong ong : adotante.getOngFavoritas()) {
            ongFavoritas.add(OngMapper.toOngFavoritaDto(ong));
        }

        return AdotanteFavoritoOngDto.builder()
                .usuarioId(adotante.getId())
                .ongFavoritas(ongFavoritas)
                .build();
    }

    public static List<AdotanteDtoListaRequisicao> toAdotanteRequisicaoDto(Adotante adotante, List<Requisicao> requisicoesAdotante) {
        List<AdotanteDtoListaRequisicao> requisicoesDto = new ArrayList<>();
        for (Requisicao requisicao : requisicoesAdotante) {
            requisicoesDto.add(AdotanteDtoListaRequisicao.builder()
                    .idRequisicao(requisicao.getId())
                    .dataAplicacao(requisicao.getDataRequisicao())
                    .idAnimal(requisicao.getAnimal().getId())
                    .nomePet(requisicao.getAnimal().getNome())
                    .imagem(requisicao.getAnimal().getFotoPerfil() != null ? requisicao.getAnimal().getFotoPerfil().getUrl() : null)
                    .status(requisicao.getStatus().getStatus())
                            .motivoRecusa(requisicao.getMotivoRecusa())
                    .build());
        }

        return requisicoesDto;
    }
}