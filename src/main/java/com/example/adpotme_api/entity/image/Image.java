package com.example.adpotme_api.entity.image;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name_image")
    private String name;

    @Column(name = "url_image")
    private String url;

    @Column(name = "public_id")
    private String publicId; // Campo para armazenar o public_id do Cloudinary
}