// PasswordResetService.java
package com.example.adpotme_api.service;

import com.example.adpotme_api.dto.adotante.PasswordResetRequestDto;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.repository.AdotanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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


        String code = gerarCodigo(adotante);


        String mensagem = String.format(
                """
                        Olá %s, 
                                
                        Você solicitou uma redefinição de senha para sua conta no AdoteMe. 
                        Aqui está o código de verificação para redefinir sua senha:
                                
                        Código: %s
                                
                        Atenção: este código expira em 15 minutos.
                                
                        Caso você não tenha solicitado essa redefinição, por favor, ignore este e-mail.
                                
                        Atenciosamente,
                        Equipe AdoteMe
                        """, adotante.getNome(), code
        );

        emailService.enviarEmail(adotante.getEmail(), "Redefinição de senha AdoteMe", mensagem);
    }


    public boolean validarCodigo(String email, String code) {
        Adotante adotante = adotanteRepository.findByEmail(email);
        if (adotante == null) {
            throw new RuntimeException("Adotante não encontrado");
        }

        if (!code.equals(adotante.getResetCode())) {
            return false;
        }

        LocalDateTime expiration = adotante.getResetCodeExpiration().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        if (expiration == null || expiration.isBefore(now)) {
            return false;
        }

        return true;
    }


    private String gerarCodigo(Adotante adotante) {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);

        adotante.setResetCode(String.valueOf(code));
        adotante.setResetCodeExpiration(LocalDateTime.now().plusMinutes(15).truncatedTo(ChronoUnit.SECONDS));

        adotanteRepository.save(adotante);

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