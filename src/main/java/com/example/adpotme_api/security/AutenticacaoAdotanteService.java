package com.example.adpotme_api.security;

import com.example.adpotme_api.entity.adotante.Adotante;
import com.example.adpotme_api.repository.AdotanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoAdotanteService implements UserDetailsService {

    @Autowired
    private AdotanteRepository adotanteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adotanteRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Adotante não encontrado com email: " + username));
    }
}
