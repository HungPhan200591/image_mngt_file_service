package lazy.demo.image_mngt_file_service.repository;

import lazy.demo.image_mngt_file_service.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}
