package lazy.demo.image_mngt_file_service.csv;

import com.github.javafaker.Faker;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GenerateCsv {
    private static final int TOTAL_RECORDS = 1000000;
    private static final int THREAD_COUNT = 20;  // Số luồng (threads)
    private static final int BUFFER_SIZE = 1024 * 1024; // 1MB buffer size for file operations

    public static void main(String[] args) {
        System.out.println("Bắt đầu tạo " + TOTAL_RECORDS + " bản ghi vào file CSV.");
        LocalDateTime startTime = LocalDateTime.now();
        System.out.println("Thời gian bắt đầu: " + startTime);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        int recordsPerThread = TOTAL_RECORDS / THREAD_COUNT;
        List<String> csvFiles = new ArrayList<>();
        String targetFile = "100million.csv";

        clearCsvFile(targetFile);

        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * recordsPerThread;
            int end = start + recordsPerThread;
            String csvFile = "100million_part_" + i + ".csv";  // Lưu từng phần vào file khác nhau
            csvFiles.add(csvFile);
            executorService.submit(() -> generateCsv(csvFile, start, end));
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mergeCsvFiles(targetFile, csvFiles);

        System.out.println("Hoàn thành tạo " + TOTAL_RECORDS + " bản ghi vào file CSV.");
        LocalDateTime endTime = LocalDateTime.now();
        System.out.println("Thời gian kết thúc: " + endTime);
        System.out.println("Thời gian chạy: " + Duration.between(startTime, endTime).getSeconds() + " giây");
    }

    private static void generateCsv(String csvFile, int start, int end) {
        Faker faker = new Faker();
        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile), BUFFER_SIZE)) {
            // Ghi header
            writer.write("userId,url,imageFileName,uploadedAt,mimeType,imageType,imageWidth,imageHeight,imageRatio,imageRotation,imageScale,imageArtist,size");
            writer.newLine();

            StringBuilder sb = new StringBuilder();
            for (int i = start; i < end; i++) {
                sb.setLength(0);  // Reset StringBuilder
                sb.append(faker.idNumber().valid()).append(',')
                        .append(faker.internet().url()).append(',')
                        .append(faker.file().fileName()).append(',')
                        .append(LocalDateTime.now().minusDays(random.nextInt(365))).append(',')
                        .append("image/").append(random.nextBoolean() ? "jpeg" : "png").append(',')
                        .append(random.nextBoolean() ? "thumbnail" : "full").append(',')
                        .append(random.nextInt(5000) + 100).append(',')
                        .append(random.nextInt(5000) + 100).append(',')
                        .append(String.format("%.2f", random.nextDouble())).append(',')
                        .append(random.nextInt(360)).append(',')
                        .append(String.format("%.2f", random.nextDouble() * 10)).append(',')
                        .append(faker.artist().name()).append(',')
                        .append(random.nextInt(5000000) + 50000);

                writer.write(sb.toString());
                writer.newLine();

                // Hiển thị tiến độ mỗi 100,000 bản ghi thay vì 10,000 để giảm thời gian in ra
                if (i != 0 && (i - start) % 10000 == 0) {
                    System.out.println("Thread " + Thread.currentThread().getId() + " đã tạo " + (i - start) + " bản ghi");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mergeCsvFiles(String targetFile, List<String> csvFiles) {
        // Merge các file CSV thành một file duy nhất
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile), BUFFER_SIZE)) {
            for (int i = 0; i < csvFiles.size(); i++) {
                try (BufferedReader reader = new BufferedReader(new FileReader(csvFiles.get(i)), BUFFER_SIZE)) {
                    String line;
                    // Bỏ qua header của các file nhỏ ngoại trừ file đầu tiên
                    if (i > 0) {
                        reader.readLine();
                    }
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
            System.out.println("Đã ghép file CSV thành công.");

            // Xóa các file gốc sau khi ghép
            for (String inputFile : csvFiles) {
                File file = new File(inputFile);
                if (file.delete()) {
                    System.out.println("Đã xóa file: " + inputFile);
                } else {
                    System.out.println("Không thể xóa file: " + inputFile);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearCsvFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            // Mở file với tham số append = false để ghi đè toàn bộ nội dung file
            writer.write("");  // Ghi nội dung rỗng vào file
            System.out.println("Đã xóa tất cả dữ liệu trong file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Không thể xóa dữ liệu trong file: " + filePath);
        }
    }
}
