package lazy.demo.image_mngt_file_service.controller;

import lazy.demo.image_mngt_file_service.dto.req.MinioUploadRequest;
import lazy.demo.image_mngt_file_service.dto.resp.GenericResponse;
import lazy.demo.image_mngt_file_service.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file/image/minio")
public class MinioController {

    private final MinioService minioService;

    @PostMapping("/upload")
    public ResponseEntity<GenericResponse<String>> uploadFile(MinioUploadRequest minioUploadRequest) {

        System.out.println("minioUploadRequest = " + minioUploadRequest);
        return ResponseEntity.ok(GenericResponse.success(minioService.uploadFile(minioUploadRequest)));
    }

    @PostMapping("/upload/multipart")
    public ResponseEntity<GenericResponse<String>> uploadMultipartFile(MinioUploadRequest minioUploadRequest) throws IOException {

        System.out.println("minioUploadRequest = " + minioUploadRequest);
        return ResponseEntity.ok(GenericResponse.success(minioService.uploadMultipartFile(minioUploadRequest)));
    }
}
