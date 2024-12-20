package com.example.adpotme_api.dto.adotante;

import com.example.adpotme_api.dto.formulario.FormularioCreateDto;
import com.example.adpotme_api.entity.image.Image;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdotanteCreateDto {
    private String nome;
    @Past
    private LocalDate dtNasc;
    private String cep;
    private LocalDate cadastro = LocalDate.now();
    @Email
    private String email;
    private String senha;
    private String celular;
    private String numero;
    private FormularioCreateDto formulario;
}
