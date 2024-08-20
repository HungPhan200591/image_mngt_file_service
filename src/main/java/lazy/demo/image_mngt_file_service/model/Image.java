package lazy.demo.image_mngt_file_service.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "images")
@Data
public class Image {
    @Id
    private String id;
    private String userId;
    private String url;
    private String fileName;
    private LocalDateTime uploadedAt;
    private String mimeType;
    private long size;
}
