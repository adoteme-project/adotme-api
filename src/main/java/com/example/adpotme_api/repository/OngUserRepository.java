package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.ongUser.OngUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OngUserRepository extends JpaRepository<OngUser, Long> {
    Optional<OngUser> findByEmail(String email);
}
