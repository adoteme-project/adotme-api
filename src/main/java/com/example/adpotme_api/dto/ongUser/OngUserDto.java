package com.example.adpotme_api.dto.ongUser;

import com.example.adpotme_api.entity.ongUser.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OngUserDto {
    private long id;
    private String nome;
    private String email;
    private Role role;
    private Long ongId;
}
