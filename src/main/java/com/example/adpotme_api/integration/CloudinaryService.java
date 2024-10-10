package com.example.adpotme_api.integration;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.example.adpotme_api.entity.image.Image;
import com.example.adpotme_api.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;

    public CloudinaryService(Cloudinary cloudinary, ImageRepository imageRepository) {
        this.cloudinary = cloudinary;
        this.imageRepository = imageRepository;
    }

    public Image upload(MultipartFile file) throws IOException {
        // Faz o upload do arquivo para o Cloudinary
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));

        // Cria uma nova instância da entidade Image e salva no banco
        Image image = new Image();
        image.setName(file.getOriginalFilename()); // Nome original do arquivo
        image.setUrl(uploadResult.get("secure_url").toString()); // URL segura do Cloudinary
        image.setPublicId(uploadResult.get("public_id").toString()); // Salva o public_id

        return imageRepository.save(image); // Salva a imagem no banco de dados
    }

    public Map<String, String> delete(String publicId) throws IOException {
        // Exclui a imagem do Cloudinary pelo publicId
        Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        return Map.of(
                "result", result.get("result").toString()
        );
    }

    public ApiResponse getImage(String publicId) {
        try {
            return cloudinary.api().resource(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            // Lidar com exceções, se necessário
            e.printStackTrace();
            return null;
        }
    }
}
