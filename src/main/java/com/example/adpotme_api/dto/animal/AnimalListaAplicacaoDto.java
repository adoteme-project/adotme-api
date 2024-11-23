package com.example.adpotme_api.dto.animal;

import com.example.adpotme_api.dto.personalidade.PersonalidadeAnimalOngResponseDto;
import com.example.adpotme_api.dto.requisicao.RequisicaoDto;
import com.example.adpotme_api.dto.requisicao.RequisicaoReadDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AnimalListaAplicacaoDto {
    private Long id;
    private String nome;
    private String fotoPerfil;
    private String descricao;
    private Double taxaAdocao;
    private String especie;
    private String raca;
    private String sexo;
    private Integer idade;
    private String dataAbrigo;
    private String tamanho;
    private PersonalidadeAnimalOngResponseDto personalidade;
    private List<RequisicaoDto> requisicoes;
}
