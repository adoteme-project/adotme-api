// PasswordResetController.java
package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.adotante.PasswordResetCodeDto;
import com.example.adpotme_api.dto.adotante.PasswordResetDto;
import com.example.adpotme_api.dto.adotante.PasswordResetRequestDto;
import com.example.adpotme_api.service.PasswordResetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/redefinicao-senha")
@Tag(name = "Redefinição de Senha", description = "Controlador para operações relacionadas a redefinição de senha.")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/request-code")
    public ResponseEntity<Void> requestPasswordResetCode(@RequestBody PasswordResetRequestDto requestDto) {
        passwordResetService.requestPasswordResetCode(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validar-codigo")
    public ResponseEntity<Boolean> validateResetCode(@RequestBody PasswordResetCodeDto dto) {
        boolean isValid = passwordResetService.validarCodigo(dto.getEmail(), dto.getVerificationCode());
        return ResponseEntity.ok(isValid);
    }

    @PutMapping("/resetar-senha")
    public ResponseEntity<Void> resetPassword(@RequestBody PasswordResetDto resetPasswordRequestDto) {
        passwordResetService.resetarSenha(resetPasswordRequestDto.getEmail(), resetPasswordRequestDto.getNewPassword());
        return ResponseEntity.ok().build();
    }

}