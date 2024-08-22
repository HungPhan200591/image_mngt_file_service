package lazy.demo.image_mngt_file_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Images")
public class Image {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID imageId;
    private String userId;
    private String url;
    private String fileName;
    private LocalDateTime uploadedAt;
    private String mimeType;
    private long size;
}
