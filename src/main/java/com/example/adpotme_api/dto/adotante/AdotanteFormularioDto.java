package com.example.adpotme_api.dto.adotante;

import com.example.adpotme_api.dto.endereco.EnderecoDto;
import com.example.adpotme_api.dto.endereco.EnderecoResponseOngDto;
import com.example.adpotme_api.dto.formulario.FormularioCreateDto;
import com.example.adpotme_api.dto.formulario.FormularioResponseAdotanteDto;
import lombok.*;
import org.springframework.security.config.annotation.web.saml2.Saml2SecurityMarker;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdotanteFormularioDto {
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String telefone;
    private String fotoPerfil;
    private EnderecoResponseOngDto endereco;
    private FormularioResponseAdotanteDto formulario;

}
