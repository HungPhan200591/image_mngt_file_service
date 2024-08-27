package lazy.demo.image_mngt_file_service.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageFileNameCount {
    @org.springframework.data.mongodb.core.mapping.Field("_id")
    private String firstLetter;  // Chữ cái đầu tiên của image_file_name

    private long count;  // Số lượng bản ghi bắt đầu với chữ cái đó

}
