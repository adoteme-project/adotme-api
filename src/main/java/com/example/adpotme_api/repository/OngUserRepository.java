package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.ong.Ong;
import com.example.adpotme_api.entity.ongUser.OngUser;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface OngUserRepository extends JpaRepository<OngUser, Long> {
    OngUser findByEmail(String email);

    boolean existsByEmail(@Email String email);


    List<OngUser> findAllByOng(Ong ong);
}
