package com.example.adpotme_api.entity.dadosBancarios;

import com.example.adpotme_api.entity.image.Image;
import com.example.adpotme_api.entity.ong.Ong;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dados_bancarios")
@Getter
@Setter
public class DadosBancarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String banco;
    private String agencia;
    private String conta;
    private String tipoConta;
    private String chavePix;
    @OneToOne
    private Image qrCode;
    private String nomeTitular;
    @OneToOne
    @JoinColumn(name = "ong_id")
    private Ong ong;

}
