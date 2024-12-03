package com.example.adpotme_api.dto.adotante;

import com.example.adpotme_api.dto.formulario.FormularioResponseAdotanteDto;
import com.example.adpotme_api.entity.endereco.Endereco;
import com.example.adpotme_api.entity.formulario.Formulario;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdotanteResponseDto {
        public Long id;
        public String nome;
        public LocalDate dtNasc;
        public LocalDate cadastro;
        public String email;
        public String senha;
        public String celular;
        public String fotoPerfil;
        public FormularioResponseAdotanteDto formulario;
        public Endereco endereco;
}
