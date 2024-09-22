package com.example.adpotme_api.security;

import com.example.adpotme_api.repository.AdotanteRepository;
import com.example.adpotme_api.repository.OngUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private OngUserRepository ongUserRepository;

    @Autowired
    private AdotanteRepository adotanteRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);

            // Tente encontrar o usuário no OngUserRepository
            var ongUser = ongUserRepository.findByEmail(subject);
            if (ongUser != null) {

                var authentication = new UsernamePasswordAuthenticationToken(ongUser, null, ongUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Se não encontrado no OngUserRepository, tente no AdotanteRepository
                var adotante = adotanteRepository.findByEmail(subject);
                if (adotante != null) {

                    var authentication = new UsernamePasswordAuthenticationToken(adotante, null, adotante.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // Opcional: Registrar um aviso ou tomar alguma ação quando o usuário não for encontrado em nenhum repositório
                    logger.warn("Usuário não encontrado para o email: " + subject);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
