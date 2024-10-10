package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.animal.Animal;
import com.example.adpotme_api.entity.ong.Ong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByOng(Ong ong);
}
