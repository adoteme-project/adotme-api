package com.example.adpotme_api.dto.ong;

import com.example.adpotme_api.entity.image.Image;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OngPutDto {
    private String nome;
    private String email;
    private String celular;
    private String site;
    private String instagram;
    private String facebook;
    private String telefone;
    private String descricao;
}
