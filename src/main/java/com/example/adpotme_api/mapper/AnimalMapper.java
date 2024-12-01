package com.example.adpotme_api.mapper;

import com.example.adpotme_api.dto.animal.*;
import com.example.adpotme_api.dto.requisicao.RequisicaoDto;
import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.image.Image;
import com.example.adpotme_api.entity.requisicao.Requisicao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AnimalMapper {
    public static AnimalOngResponseDto toAnimalOngResponseDto(Animal animal) {
        List<Image> animalImages = animal.getFotos();
        String imagem2 = !animalImages.isEmpty() ? animalImages.get(0).getUrl() : null;
        String imagem3 = animalImages.size() >= 2 ? animalImages.get(1).getUrl() : null;
        String imagem4 = animalImages.size() >= 3 ? animalImages.get(2).getUrl() : null;
        String imagem5 = animalImages.size() >= 4 ? animalImages.get(3).getUrl() : null;
        AnimalOngResponseDto animalOngResponseDto = new AnimalOngResponseDto();
        animalOngResponseDto.setId(animal.getId());
        animalOngResponseDto.setImagem(animal.getFotoPerfil().getUrl() != null ? animal.getFotoPerfil().getUrl() : null);
        animalOngResponseDto.setImagem2(imagem2);
        animalOngResponseDto.setImagem3(imagem3);
        animalOngResponseDto.setImagem4(imagem4);
        animalOngResponseDto.setImagem5(imagem5);
        animalOngResponseDto.setNome(animal.getNome());
        animalOngResponseDto.setPorte(animal.getPorte());
        animalOngResponseDto.setSexo(animal.getSexo());
        animalOngResponseDto.setEspecie(animal.getEspecie().getEspecie());
        animalOngResponseDto.setIdade(LocalDate.now().getYear() - animal.getAnoNascimento());
        animalOngResponseDto.setPersonalidade(PersonalidadeMapper.toDto(animal.getPersonalidade()));
        return animalOngResponseDto;

    }
    public static AnimalCsvDto toAnimalCsvDto(Animal animal) {
        AnimalCsvDto animalCsvDto = new AnimalCsvDto();
        animalCsvDto.setId(animal.getId());
        animalCsvDto.setNome(animal.getNome());
        animalCsvDto.setAnoNascimento(animal.getAnoNascimento());
        animalCsvDto.setSexo(animal.getSexo());
        animalCsvDto.setEspecie(animal.getEspecie().getEspecie());
        animalCsvDto.setRaca(animal.getRaca());
        animalCsvDto.setDataAbrigo(animal.getDataAbrigo().toString());
        animalCsvDto.setCadastro(animal.getCadastro().toString());
        animalCsvDto.setPorte(animal.getPorte());
        animalCsvDto.setDescricao(animal.getDescricao());
        animalCsvDto.setIsCastrado(animal.getIsCastrado());
        animalCsvDto.setIsVisible(animal.getIsVisible());
        animalCsvDto.setIsAdotado(animal.getIsAdotado());
        animalCsvDto.setTaxaAdocao(animal.getTaxaAdocao());
        return animalCsvDto;
    }

    public static AnimalOngDto toAnimalOngDto(Animal animal) {
        AnimalOngDto animalOngDto = new AnimalOngDto();
        animalOngDto.setId(animal.getId());
        animalOngDto.setImagem(animal.getFotoPerfil().getUrl() != null ? animal.getFotoPerfil().getUrl() : null);
        animalOngDto.setNome(animal.getNome());
        animalOngDto.setPorte(animal.getPorte());
        animalOngDto.setEspecie(animal.getEspecie().getEspecie());
        animalOngDto.setIdade(LocalDate.now().getYear() - animal.getAnoNascimento());
        return animalOngDto;
    }

    public static AnimalFavoritoDto toAnimalFavorito(Animal animal){
        return AnimalFavoritoDto.builder()
                .animalId(animal.getId())
                .nome(animal.getNome())
                .idade(LocalDate.now().getYear() - animal.getAnoNascimento())
                .especie(animal.getEspecie().getEspecie())
                .sexo(animal.getSexo())
                .porte(animal.getPorte())
                .distancia(0)
                .imagem(animal.getFotoPerfil().getUrl())
                .build();
    }

    public static AnimalAplicacaoDto toAnimalAplicacaoDto(Animal animal, Integer qtdAplicacao, String situacao, LocalDateTime enviado) {
        return AnimalAplicacaoDto.builder()
                .id(animal.getId())
                .nome(animal.getNome())
                .qtdAplicacoes(qtdAplicacao)
                .enviado(enviado)
                .taxaAdocao(animal.getTaxaAdocao())
                .dataEntrada(animal.getCadastro())
                .situacao(situacao)
                .build();
    }

    public static RequisicaoDto toRequisicaoDto(Requisicao requisicao) {
        return RequisicaoDto.builder()
                .id(requisicao.getId())
                .formularioId(requisicao.getFormulario().getId())
                .nomeAdotante(requisicao.getFormulario().getAdotante().getNome())
                .email(requisicao.getFormulario().getAdotante().getEmail())
                .dataRequisicao(requisicao.getDataRequisicao())
                .status(requisicao.getStatus().getStatus())
                .build();
    }

    public static AnimalListaAplicacaoDto toAnimalListaAplicacaoDto(Animal animal, List<RequisicaoDto> requisicoesDto) {
        return AnimalListaAplicacaoDto.builder()
                .id(animal.getId())
                .nome(animal.getNome())
                .taxaAdocao(animal.getTaxaAdocao())
                .fotoPerfil(animal.getFotoPerfil() != null ? animal.getFotoPerfil().getUrl() : null)
                .descricao(animal.getDescricao())
                .especie(animal.getEspecie().getEspecie())
                .raca(animal.getRaca())
                .sexo(animal.getSexo())
                .idade(LocalDate.now().getYear() - animal.getAnoNascimento())
                .dataAbrigo(animal.getDataAbrigo().toString())
                .tamanho(animal.getPorte())
                .personalidade(PersonalidadeMapper.toPersonalidadeAnimal(animal.getPersonalidade()))
                .requisicoes(requisicoesDto)
                .build();
    }
}
