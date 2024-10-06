package com.example.adpotme_api.dto.adotante;

import com.example.adpotme_api.entity.image.Image;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class AdotanteCreateDto {
    private String nome;
    private LocalDate dtNasc;
    private String cep;
    private LocalDate cadastro = LocalDate.now();
    @Email
    private String email;
    private String senha;
    private String celular;
    private String numero;
}
