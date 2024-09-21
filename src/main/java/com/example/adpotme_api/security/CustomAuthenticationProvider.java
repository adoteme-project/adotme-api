package com.example.adpotme_api.security;

import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.ongUser.OngUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AutenticacaoOngUserService ongUserService;
    private final AutenticacaoAdotanteService adotanteService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(AutenticacaoOngUserService ongUserService,
                                        AutenticacaoAdotanteService adotanteService,
                                        PasswordEncoder passwordEncoder) {
        this.ongUserService = ongUserService;
        this.adotanteService = adotanteService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String senha = (String) authentication.getCredentials();

        UserDetails user = null;

        try {

            user = ongUserService.loadUserByUsername(email);
            if (user != null && passwordEncoder.matches(senha, user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(user, senha, user.getAuthorities());
            }
        } catch (UsernameNotFoundException e) {

            System.out.println("Usuário OngUser não encontrado com o email: " + email);
        }

        try {

            user = adotanteService.loadUserByUsername(email);
            if (user != null && passwordEncoder.matches(senha, user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(user, senha, user.getAuthorities());
            }
        } catch (UsernameNotFoundException e) {

            System.out.println("Usuário Adotante não encontrado com o email: " + email);
        }


        throw new BadCredentialsException("Credenciais inválidas.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
