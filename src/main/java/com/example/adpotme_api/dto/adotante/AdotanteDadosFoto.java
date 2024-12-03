package com.example.adpotme_api.dto.adotante;

import com.example.adpotme_api.dto.endereco.EnderecoDto;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdotanteDadosFoto {
    public String nome;
    public LocalDate dataNascimeto;
    public String email;
    public String telefone;
    public String urlFoto;
    public EnderecoDto endereco;
}
