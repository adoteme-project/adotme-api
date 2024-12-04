package com.example.adpotme_api.controller;

import com.cloudinary.api.ApiResponse;
import com.example.adpotme_api.entity.image.Image;
import com.example.adpotme_api.integration.CloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cloudinary")
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    public CloudinaryController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> uploadImage(@RequestPart("file") MultipartFile file) {
        try {
            Image image = cloudinaryService.upload(file);
            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteImage(@RequestParam("public_id") String publicId) {
        try {
            Map<String, String> response = cloudinaryService.delete(publicId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(Map.of("error", "Falha ao deletar a imagem."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/image")
    public ResponseEntity<?> getImage(@RequestParam("public_id") String publicId) {
        ApiResponse result = cloudinaryService.getImage(publicId);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
