package com.example.adpotme_api.controller;

import com.example.adpotme_api.dto.ongUser.OngUserLoginDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> efetuarLoginOngUser(@RequestBody @Valid OngUserLoginDto ongUserLogin) {
        var token = new UsernamePasswordAuthenticationToken(ongUserLogin.email(), ongUserLogin.senha());
        var authentication = authenticationManager.authenticate(token);
        return ResponseEntity.ok(authentication);
    }
}
