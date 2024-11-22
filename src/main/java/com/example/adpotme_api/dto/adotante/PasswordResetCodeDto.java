package com.example.adpotme_api.dto.adotante;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetCodeDto {
    private String email;
    private String verificationCode;
}
