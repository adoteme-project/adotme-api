package com.example.adpotme_api.security.ongUser;

import com.example.adpotme_api.repository.OngUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoOngUserService implements UserDetailsService {

    @Autowired
    private OngUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);

    }
}
