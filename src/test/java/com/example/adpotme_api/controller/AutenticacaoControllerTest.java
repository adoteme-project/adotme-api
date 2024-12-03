package com.example.adpotme_api.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.adpotme_api.dto.adotante.AdotanteLoginDto;
import com.example.adpotme_api.dto.adotante.AdotanteTokenDtoJWT;
import com.example.adpotme_api.dto.ongUser.OngUserLoginDto;
import com.example.adpotme_api.dto.ongUser.OngUserTokenDtoJWT;
import com.example.adpotme_api.dto.token.RefreshTokenRequest;
import com.example.adpotme_api.dto.token.TokenResponse;
import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.entity.ongUser.OngUser;
import com.example.adpotme_api.repository.AdotanteRepository;
import com.example.adpotme_api.security.TokenService;
import com.example.adpotme_api.security.adotante.AutenticacaoAdotanteService;
import com.example.adpotme_api.security.ongUser.AutenticacaoOngUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

class AutenticacaoControllerTest {

    @InjectMocks
    private AutenticacaoController autenticacaoController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AutenticacaoOngUserService authOngUserService;

    @Mock
    private AutenticacaoAdotanteService authAdotanteService;

    @Mock
    private AdotanteRepository adotanteRepository;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEfetuarLoginOngUser_Success() {

        // Arrange

        OngUserLoginDto loginDto = new OngUserLoginDto("onguser@example.com", "password");
        OngUser ongUser = new OngUser();
        ongUser.setId(1L);
        ongUser.setOng(new Ong());
        ongUser.setRole(com.example.adpotme_api.entity.ongUser.Role.ADMIN);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(ongUser);
        when(tokenService.gerarTokenOngUser(ongUser)).thenReturn("fake-jwt-token");

        // Act
        ResponseEntity<?> response = autenticacaoController.efetuarLoginOngUser(loginDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        OngUserTokenDtoJWT responseBody = (OngUserTokenDtoJWT) response.getBody();
        assertNotNull(responseBody);
        assertEquals("fake-jwt-token", responseBody.token());
        assertEquals(1L, responseBody.ongUserId());
    }

    @Test
    void testEfetuarLoginAdotante_Success() {
        // Arrange
        AdotanteLoginDto loginDto = new AdotanteLoginDto("adotante@example.com", "password");
        Adotante adotante = new Adotante();
        adotante.setId(1L);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(adotante);
        when(tokenService.gerarTokenAdotante(adotante)).thenReturn("fake-jwt-token");

        // Act
        ResponseEntity<?> response = autenticacaoController.efetuarLoginAdotante(loginDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        AdotanteTokenDtoJWT responseBody = (AdotanteTokenDtoJWT) response.getBody();
        assertNotNull(responseBody);
        assertEquals("fake-jwt-token", responseBody.token());
        assertEquals(1L, responseBody.idUser());
    }

    @Test
    void testRefreshToken_Success() {
        // Arrange
        RefreshTokenRequest request = new RefreshTokenRequest();
        Adotante adotante = new Adotante();
        adotante.setId(1L);
        adotante.setEmail("adotante@example.com");

        when(tokenService.getSubject("fake-refresh-token")).thenReturn("adotante@example.com");
        when(adotanteRepository.findByEmail("adotante@example.com")).thenReturn(adotante);
        when(tokenService.validateRefreshToken("fake-refresh-token", adotante)).thenReturn(true);
        when(tokenService.gerarTokenAdotante(adotante)).thenReturn("new-access-token");
        when(tokenService.gerarRefreshToken(adotante)).thenReturn("new-refresh-token");

        // Act
        ResponseEntity<?> response = autenticacaoController.refreshToken(request);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        TokenResponse responseBody = (TokenResponse) response.getBody();
        assertNotNull(responseBody);

    }

    @Test
    void testRefreshToken_InvalidToken() {
        // Arrange
        RefreshTokenRequest request = new RefreshTokenRequest();

        when(tokenService.getSubject("invalid-refresh-token")).thenThrow(new RuntimeException("Token inválido"));

        // Act
        ResponseEntity<?> response = autenticacaoController.refreshToken(request);

        // Assert
        assertNotNull(response);
        assertEquals(401, response.getStatusCodeValue());
    }
}
