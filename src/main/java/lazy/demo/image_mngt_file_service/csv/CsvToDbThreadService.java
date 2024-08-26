package lazy.demo.image_mngt_file_service.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lazy.demo.image_mngt_file_service.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class CsvToDbThreadService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final int THREAD_COUNT = 10;
    private static final int BATCH_SIZE = 10000;

    /**
     * Đọc dữ liệu từ file CSV và chèn vào MongoDB bằng nhiều luồng.
     */
    public void insertCsvDataToDb() {
        String csvFilePath = "100million.csv";
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            List<Image> images = new ArrayList<>();
            String[] line;

            // Bỏ qua dòng header (nếu có)
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                Image image = Image.builder()
                        ._imageId(UUID.randomUUID())
                        .userId(line[0])
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

                if (images.size() >= BATCH_SIZE) {
                    final List<Image> batch = new ArrayList<>(images);
                    executorService.submit(() -> insertBatch(batch));
                    images.clear();
                }
            }

            // Chèn những bản ghi còn lại trong danh sách
            if (!images.isEmpty()) {
                executorService.submit(() -> insertBatch(images));
            }

            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.HOURS);

            System.out.println("Đã chèn dữ liệu từ file CSV vào MongoDB thành công.");

        } catch (IOException | CsvValidationException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi đọc file CSV hoặc chèn dữ liệu vào MongoDB.");
        }
    }

    private void insertBatch(List<Image> images) {
        try {
            System.out.println("Chèn " + images.size() + " bản ghi vào MongoDB...");
            mongoTemplate.insertAll(images);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
