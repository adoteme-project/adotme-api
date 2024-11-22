// PasswordResetDto.java
package com.example.adpotme_api.dto.adotante;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordResetDto {
    private String email;
    private String newPassword;

}