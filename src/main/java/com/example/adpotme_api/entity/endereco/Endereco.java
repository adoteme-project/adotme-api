package com.example.adpotme_api.entity.endereco;

import com.example.adpotme_api.dto.endereco.EnderecoDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "endereco")
@Entity(name = "Endereco")
public class Endereco {
    private String cep;

    @JsonProperty(value = "uf")
    private String estado;

    @JsonProperty(value = "localidade")
    private String cidade;

    private String bairro;

    @JsonProperty(value = "logradouro")
    private String rua;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Endereco(EnderecoDto dto) {
        this.cep = dto.getCep();
        this.estado = dto.getEstado();
        this.cidade = dto.getCidade();
        this.bairro = dto.getBairro();
        this.rua = dto.getRua();

    }

    public Endereco(){

    }



    @Override
    public String toString() {
        return "Endereco{" +
                "cep='" + cep + '\'' +
                ", estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                '}';
    }

}
