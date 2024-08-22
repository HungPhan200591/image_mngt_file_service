package lazy.demo.image_mngt_file_service.repository;

import lazy.demo.image_mngt_file_service.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional
public interface ImageRepository extends MongoRepository<Image, UUID> {

}
