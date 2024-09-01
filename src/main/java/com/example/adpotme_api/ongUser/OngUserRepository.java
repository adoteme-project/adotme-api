package com.example.adpotme_api.ongUser;

import com.example.adpotme_api.ong.Ong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OngUserRepository extends JpaRepository<OngUser, Long> {
}