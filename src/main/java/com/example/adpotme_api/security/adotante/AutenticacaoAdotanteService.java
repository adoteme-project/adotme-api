package com.example.adpotme_api.security.adotante;

import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.repository.AdotanteRepository;
import com.example.adpotme_api.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoAdotanteService implements UserDetailsService {

    @Autowired
    private AdotanteRepository adotanteRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adotanteRepository.findByEmail(username);
    }

}
