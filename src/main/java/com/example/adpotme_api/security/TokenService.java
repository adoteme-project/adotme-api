package com.example.adpotme_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.ongUser.OngUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarTokenOngUser(OngUser ongUser) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API AdoteMe")
                    .withSubject(ongUser.getEmail())
                    .withClaim("id", ongUser.getId())
                    .withExpiresAt(dataExpiracao(2))
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String gerarTokenAdotante(Adotante adotante) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API AdoteMe")
                    .withSubject(adotante.getEmail())
                    .withClaim("id", adotante.getId())
                    .withExpiresAt(dataExpiracao(1))  // Access token expira em 1 hora
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar access token JWT", exception);
        }
    }

    public String gerarRefreshToken(Object user) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            if (user instanceof Adotante) {
                Adotante adotante = (Adotante) user;
                return JWT.create()
                        .withIssuer("API AdoteMe")
                        .withSubject(adotante.getEmail())
                        .withClaim("id", adotante.getId())
                        .withExpiresAt(dataExpiracao(48))
                        .sign(algoritmo);
            } else if (user instanceof OngUser) {
                OngUser ongUser = (OngUser) user;
                return JWT.create()
                        .withIssuer("API AdoteMe")
                        .withSubject(ongUser.getEmail())
                        .withClaim("id", ongUser.getId())
                        .withExpiresAt(dataExpiracao(48))
                        .sign(algoritmo);
            } else {
                throw new IllegalArgumentException("Tipo de usuário não suportado.");
            }
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar refresh token JWT", exception);
        }
    }

    public boolean validateRefreshToken(String refreshToken, Object user) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algoritmo)
                    .withIssuer("API AdoteMe")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            String email = decodedJWT.getSubject();

            Date expiration = decodedJWT.getExpiresAt();
            if (expiration.before(new Date())) {
                return false;
            }

            if (user instanceof Adotante adotante) {
                return email.equals(adotante.getEmail());
            } else if (user instanceof OngUser ongUser) {
                return email.equals(ongUser.getEmail());
            } else {
                return false;
            }
        } catch (JWTVerificationException exception) {
            return false;
        }
    }


    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API AdoteMe")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }


    private Instant dataExpiracao(int horas) {
        return LocalDateTime.now().plusHours(horas).toInstant(ZoneOffset.of("-03:00"));
    }
}
