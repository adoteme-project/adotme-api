package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.adotante.AdotanteLoginDto;
import com.example.adpotme_api.dto.adotante.AdotanteTokenDtoJWT;
import com.example.adpotme_api.dto.ongUser.OngUserLoginDto;
import com.example.adpotme_api.dto.ongUser.OngUserTokenDtoJWT;
import com.example.adpotme_api.dto.token.RefreshTokenRequest;
import com.example.adpotme_api.dto.token.TokenResponse;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.ongUser.OngUser;
import com.example.adpotme_api.repository.AdotanteRepository;
import com.example.adpotme_api.security.adotante.AutenticacaoAdotanteService;
import com.example.adpotme_api.security.ongUser.AutenticacaoOngUserService;
import com.example.adpotme_api.security.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/login")
@SecurityRequirement(name = "bearerAuth")
public class AutenticacaoController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AutenticacaoOngUserService authOngUserService;

    @Autowired
    private AutenticacaoAdotanteService authAdotanteService;

    @Autowired
    private AdotanteRepository adotanteRepository;

    @Autowired
    private TokenService tokenService;
    @PostMapping("/ong-user")
    public ResponseEntity<?> efetuarLoginOngUser(@RequestBody @Valid OngUserLoginDto ongUserLogin) {
            authOngUserService.loadUserByUsername(ongUserLogin.email());
            var authToken = new UsernamePasswordAuthenticationToken(ongUserLogin.email(), ongUserLogin.senha());
            var authentication = authenticationManager.authenticate(authToken);
            var tokenJWT = tokenService.gerarTokenOngUser((OngUser) authentication.getPrincipal());
            var ongUserId = ((OngUser) authentication.getPrincipal()).getId();
            var ongId = ((OngUser) authentication.getPrincipal()).getOngId();
            var role = ((OngUser) authentication.getPrincipal()).getRole();
            return ResponseEntity.ok(new OngUserTokenDtoJWT(tokenJWT, ongUserId, ongId, role));

    }

    @PostMapping("/adotante")
    public ResponseEntity<?> efetuarLoginAdotante(@RequestBody @Valid AdotanteLoginDto adotanteLogin) {

            var authToken = new UsernamePasswordAuthenticationToken(adotanteLogin.email(), adotanteLogin.senha());
            var authentication = authenticationManager.authenticate(authToken);

            var tokenJWT = tokenService.gerarTokenAdotante((Adotante) authentication.getPrincipal());
            var adotanteId = ((Adotante) authentication.getPrincipal()).getId();

            return ResponseEntity.ok(new AdotanteTokenDtoJWT(tokenJWT, adotanteId));

    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        // Refatorar em um service depois
        String refreshToken = request.getRefreshToken();
        try {
            String email = tokenService.getSubject(refreshToken);
            Adotante adotante = adotanteRepository.findByEmail(email);

            if (adotante == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Adotante não encontrado.");
            }

            // Verifica se o refresh token fornecido é válido
            boolean isValidRefreshToken = tokenService.validateRefreshToken(refreshToken, adotante);
            if (!isValidRefreshToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token inválido.");
            }

            // Gera novo access token e refresh token
            String novoAccessToken = tokenService.gerarTokenAdotante(adotante);
            String novoRefreshToken = tokenService.gerarRefreshToken(adotante);

            // Retorna os tokens gerados
            return ResponseEntity.ok(new TokenResponse(novoAccessToken, novoRefreshToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token inválido ou expirado.");
        }
    }
}
