package com.example.adpotme_api.dto.adotante;

import com.example.adpotme_api.dto.formulario.FormularioResponseAdotanteDto;
import com.example.adpotme_api.entity.endereco.Endereco;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdotanteResponseDto {
        private Long id;
        private String nome;
        private LocalDate dtNasc;
        private LocalDate cadastro;
        private String email;
        private String senha;
        private String celular;
        private String numero;
        private String fotoPerfil;
        private FormularioResponseAdotanteDto formulario;
        private Endereco endereco;
}
