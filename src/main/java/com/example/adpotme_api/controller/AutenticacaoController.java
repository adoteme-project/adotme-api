package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.adotante.AdotanteLoginDto;
import com.example.adpotme_api.dto.ongUser.OngUserLoginDto;
import com.example.adpotme_api.entity.ongUser.OngUser;
import com.example.adpotme_api.security.AutenticacaoAdotanteService;
import com.example.adpotme_api.security.AutenticacaoOngUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AutenticacaoOngUserService authOngUserService;

    @Autowired
    private AutenticacaoAdotanteService authAdotanteService;

    @PostMapping("/ong-user")
    public ResponseEntity<?> efetuarLoginOngUser(@RequestBody @Valid OngUserLoginDto ongUserLogin) {

        authOngUserService.loadUserByUsername(ongUserLogin.email());
        var token = new UsernamePasswordAuthenticationToken(ongUserLogin.email(), ongUserLogin.senha());
        var authentication = authenticationManager.authenticate(token);

        return ResponseEntity.ok(authentication);
    }

    @PostMapping("adotante")
    public ResponseEntity<?> efetuarLoginAdotante(@RequestBody @Valid AdotanteLoginDto adotanteLogin) {
        authAdotanteService.loadUserByUsername(adotanteLogin.email());
        var token = new UsernamePasswordAuthenticationToken(adotanteLogin.email(), adotanteLogin.senha());
        var authentication = authenticationManager.authenticate(token);

        return ResponseEntity.ok(authentication);
    }
}
