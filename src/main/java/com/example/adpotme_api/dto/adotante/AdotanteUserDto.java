package com.example.adpotme_api.dto.adotante;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdotanteUserDto {
    private long id;
    private String nome;
    private String email;
}
