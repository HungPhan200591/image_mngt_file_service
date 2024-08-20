package lazy.demo.image_mngt_file_service.service;

import lazy.demo.image_mngt_file_service.model.Image;
import lazy.demo.image_mngt_file_service.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Image uploadImage(String userId, String url, String fileName, String mimeType, long size) {
        Image image = new Image();
        image.setUserId(userId);
        image.setUrl(url);
        image.setFileName(fileName);
        image.setUploadedAt(LocalDateTime.now());
        image.setMimeType(mimeType);
        image.setSize(size);
        return imageRepository.save(image);
    }

    public void deleteImage(String id) {
        imageRepository.deleteById(id);
    }
}
