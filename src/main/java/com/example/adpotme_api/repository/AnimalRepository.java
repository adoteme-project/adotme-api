package com.example.adpotme_api.repository;

import com.example.adpotme_api.entity.animal.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
