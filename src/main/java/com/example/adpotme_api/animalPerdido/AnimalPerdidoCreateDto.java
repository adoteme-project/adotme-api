package com.example.adpotme_api.animalPerdido;

import com.example.adpotme_api.endereco.Endereco;
import com.example.adpotme_api.endereco.EnderecoDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AnimalPerdidoCreateDto {
    protected String apelido;
    protected String sexo;
    protected String cep;
    protected LocalDate cadastro = LocalDate.now();
    protected String especie;
    protected String raca;
    protected String descricao;
    protected Boolean isVisible;
    protected Boolean isEncontrado;
    protected String porte;
    @NotNull(message = "ONG não pode ser nulo")
    private Long ongId; // ID da ONG associada
}
