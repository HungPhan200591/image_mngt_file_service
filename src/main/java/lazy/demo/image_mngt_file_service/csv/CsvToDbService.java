package lazy.demo.image_mngt_file_service.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lazy.demo.image_mngt_file_service.model.Image;
import lazy.demo.image_mngt_file_service.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CsvToDbService {

    @Autowired
    private ImageRepository imageRepository;

    /**
     * Đọc dữ liệu từ file CSV và chèn vào MongoDB.
     *
     */
    public void insertCsvDataToDb(MultipartFile file) {
        // Kiểm tra xem file có rỗng không
        if (file.isEmpty()) {
            System.out.println("File CSV không hợp lệ hoặc rỗng.");
            return;
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<Image> images = new ArrayList<>();
            String[] line;

            // Bỏ qua dòng header (nếu có)
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                Image image = Image.builder()
                        ._imageId(UUID.randomUUID())
                        .userId(Long.parseLong(line[0]))
                        .url(line[1])
                        .imageFileName(line[2])
                        .uploadedAt(LocalDateTime.parse(line[3]))
                        .mimeType(line[4])
                        .imageType(line[5])
                        .imageWidth(Integer.parseInt(line[6]))
                        .imageHeight(Integer.parseInt(line[7]))
                        .imageRatio(Double.parseDouble(line[8]))
                        .imageRotation(Integer.parseInt(line[9]))
                        .imageScale(Double.parseDouble(line[10]))
                        .imageArtist(line[11])
                        .size(Long.parseLong(line[12]))
                        .build();

                images.add(image);

                // Nếu danh sách đã đủ lớn (ví dụ 1000 bản ghi), hãy chèn vào DB
                if (images.size() == 1000) {
                    System.out.println("Chèn 1000 bản ghi vào MongoDB...");
                    imageRepository.saveAll(images);
                    images.clear(); // Xóa danh sách sau khi chèn
                }
            }

            // Chèn những bản ghi còn lại trong danh sách
            if (!images.isEmpty()) {
                imageRepository.saveAll(images);
            }

            System.out.println("Đã chèn dữ liệu từ file CSV vào MongoDB thành công.");

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi đọc file CSV hoặc chèn dữ liệu vào MongoDB.");
        }
    }

}
