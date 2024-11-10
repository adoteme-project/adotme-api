// PasswordResetService.java
package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.adotante.PasswordResetRequestDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.repository.AdotanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordResetService {

    @Autowired
    private AdotanteRepository adotanteRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void requestPasswordResetCode(PasswordResetRequestDto requestDto) {
        Adotante adotante = adotanteRepository.findByEmail(requestDto.getEmail());
        if (adotante == null) {
            throw new RuntimeException("Adotante não encontrado");
        }


        String code = gerarCodigo();
        adotante.setResetCode(code);
        adotanteRepository.save(adotante);
        emailService.enviarEmail(adotante.getEmail(), "Redefinição de senha AdoteMe", "Seu código de redefinição de senha é: " + code);
    }

    public boolean validarCodigo(String email, String code) {
        Adotante adotante = adotanteRepository.findByEmail(email);
        if (adotante == null) {
            throw new RuntimeException("Adotante não encontrado");
        }
        return code.equals(adotante.getResetCode());

    }

    private String gerarCodigo() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void resetarSenha(String email, String newPassword) {
        Adotante adotante = adotanteRepository.findByEmail(email);
        if (adotante == null) {
            throw new RuntimeException("Adotante não encontrado");
        }

        adotante.setSenha(passwordEncoder.encode(newPassword));

        adotante.setResetCode(null);
        adotanteRepository.save(adotante);
    }
}