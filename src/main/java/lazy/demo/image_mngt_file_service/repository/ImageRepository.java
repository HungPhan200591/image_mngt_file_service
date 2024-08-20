package lazy.demo.image_mngt_file_service.repository;

import lazy.demo.image_mngt_file_service.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends MongoRepository<Image, String> {
}
