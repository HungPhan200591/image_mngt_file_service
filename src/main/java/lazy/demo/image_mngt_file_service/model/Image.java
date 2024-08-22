package lazy.demo.image_mngt_file_service.model;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "image")
public class Image {
    @Id
    private UUID _imageId;
    private String userId;
    private String url;
    private String imageFileName;
    private LocalDateTime uploadedAt;
    private String mimeType;
    private String imageType;
    private Integer imageWidth;
    private Integer imageHeight;
    private Double imageRatio;
    private Integer imageRotation;
    private Double imageScale;
    private String imageArtist;
    private long size;

}
