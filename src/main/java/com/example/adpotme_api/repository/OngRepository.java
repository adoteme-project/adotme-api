package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.ong.Ong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OngRepository extends JpaRepository<Ong, Long> {
    List<Ong> findAllByOrderByNomeDesc();
}
