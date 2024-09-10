package com.example.adpotme_api.ong;

import com.example.adpotme_api.endereco.Endereco;
import com.example.adpotme_api.endereco.EnderecoDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OngCreateDto {

    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "O telefone não pode estar em branco")
    private String telefone;

    @NotBlank(message = "O CNPJ não pode estar em branco")
    private String cnpj;
    private String cep;



}
