package com.example.adpotme_api.entity.adocao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "logAdocao")
@Entity(name = "logAdocao")
@Getter
@Setter
public class LogAdocao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ongId;
    private String nomeAdotante;
    private String nomeAnimal;
    private LocalDate data;
    private String tipo;

}
