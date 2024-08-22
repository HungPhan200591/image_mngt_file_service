package lazy.demo.image_mngt_file_service.service;

import lazy.demo.image_mngt_file_service.model.Image;
import lazy.demo.image_mngt_file_service.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public Image updateImage(UUID id, Image imageDetails) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        image.setUserId(imageDetails.getUserId());
        image.setUrl(imageDetails.getUrl());
        image.setFileName(imageDetails.getFileName());
        image.setUploadedAt(imageDetails.getUploadedAt());
        image.setMimeType(imageDetails.getMimeType());
        image.setSize(imageDetails.getSize());
        return imageRepository.save(image);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Image getImageById(UUID id) {
        return imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    public void deleteImage(UUID id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        imageRepository.delete(image);
    }
}