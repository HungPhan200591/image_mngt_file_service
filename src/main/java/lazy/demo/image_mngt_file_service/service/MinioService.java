package lazy.demo.image_mngt_file_service.service;

import lazy.demo.image_mngt_file_service.dto.req.MinioUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.AbortMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.model.UploadPartResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final S3Client s3Client;

    @Value("${minio.imageBucketName}")
    private String imageBucketName;

    public String uploadFile(MinioUploadRequest minioUploadRequest) {
        MultipartFile file = minioUploadRequest.getFile();
        String objectKey = file.getOriginalFilename();
        try {
            // Tạo request để upload file lên MinIO/S3
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(imageBucketName)
                            .key(objectKey)
                            .build(),
                    RequestBody.fromBytes(file.getBytes())); // Chuyển đổi file Multipart thành byte array để upload
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file: " + objectKey;
        }

        return "Uploaded file success: " + objectKey;
    }

    public String uploadMultipartFile(MinioUploadRequest minioUploadRequest) throws IOException {
        MultipartFile file = minioUploadRequest.getFile();
        String objectKey = file.getOriginalFilename();
        CreateMultipartUploadRequest createRequest = CreateMultipartUploadRequest.builder()
                .bucket(imageBucketName)
                .key(objectKey)
                .build();

        CreateMultipartUploadResponse response = s3Client.createMultipartUpload(createRequest);
        String uploadId = response.uploadId();

        List<CompletedPart> completedParts = new ArrayList<>();
        int partSize = 50 * 1024 * 1024; // 5MB
        byte[] buffer = new byte[partSize];

        // Tạo ExecutorService với số lượng luồng tùy chỉnh
        int numberOfThreads = 10; // Số luồng bạn muốn sử dụng
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<CompletedPart>> futures = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            int bytesRead;
            int partNumber = 1;

            while ((bytesRead = inputStream.read(buffer)) != -1) {

                byte[] data = new byte[bytesRead];
                System.arraycopy(buffer, 0, data, 0, bytesRead);

                final int currentPartNumber = partNumber;
                final int currentBytesRead = bytesRead;
                Callable<CompletedPart> task = () -> {
                    // Tải lên từng phần
                    UploadPartRequest uploadRequest = UploadPartRequest.builder()
                            .bucket(imageBucketName)
                            .key(objectKey)
                            .uploadId(uploadId)
                            .partNumber(currentPartNumber)
                            .contentLength((long) currentBytesRead)
                            .build();

                    UploadPartResponse uploadPartResponse = s3Client.uploadPart(uploadRequest, RequestBody.fromBytes(data));

                    // Trả về CompletedPart khi hoàn thành
                    return CompletedPart.builder()
                            .partNumber(currentPartNumber)
                            .eTag(uploadPartResponse.eTag())
                            .build();
                };

                // Thực thi tác vụ tải lên trong thread pool
                futures.add(executorService.submit(task));

                System.out.println("Uploaded part: " + partNumber);
                partNumber++;
            }

            // Chờ tất cả các tác vụ tải lên hoàn thành
            for (Future<CompletedPart> future : futures) {
                completedParts.add(future.get());
            }
        } catch (Exception e) {
            s3Client.abortMultipartUpload(AbortMultipartUploadRequest.builder()
                    .bucket(imageBucketName)
                    .key(objectKey)
                    .uploadId(uploadId)
                    .build());
            executorService.shutdown();
            throw new RuntimeException("Error occurred while uploading file", e);
        }

        // Hoàn tất multipart upload
        CompleteMultipartUploadRequest completeRequest = CompleteMultipartUploadRequest.builder()
                .bucket(imageBucketName)
                .key(objectKey)
                .uploadId(uploadId)
                .multipartUpload(CompletedMultipartUpload.builder().parts(completedParts).build())
                .build();

        s3Client.completeMultipartUpload(completeRequest);
        executorService.shutdown();

        return "Uploaded file success: " + objectKey;
    }
}

