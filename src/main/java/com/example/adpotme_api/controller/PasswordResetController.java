// PasswordResetController.java
package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.adotante.PasswordResetRequestDto;
import com.example.adpotme_api.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redefinicao-senha")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/request-code")
    public ResponseEntity<Void> requestPasswordResetCode(@RequestBody PasswordResetRequestDto requestDto) {
        passwordResetService.requestPasswordResetCode(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validar-codigo")
    public ResponseEntity<Boolean> validateResetCode(@RequestParam String email, @RequestParam String code) {
        boolean isValid = passwordResetService.validarCodigo(email, code);
        return ResponseEntity.ok(isValid);
    }
    @PutMapping("/resetar-senha")
    public ResponseEntity<Void> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        passwordResetService.resetarSenha(email, newPassword);
        return ResponseEntity.ok().build();
    }
}