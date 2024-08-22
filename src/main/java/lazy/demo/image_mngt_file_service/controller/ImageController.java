package lazy.demo.image_mngt_file_service.controller;

import lazy.demo.image_mngt_file_service.dto.resp.GenericResponse;
import lazy.demo.image_mngt_file_service.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file/image")
public class ImageController {

    private final ImageService imageService;

    @GetMapping()
    public ResponseEntity<GenericResponse<?>> getAllImages() {
        return ResponseEntity.ok(GenericResponse.success(imageService.getAllImages()));
    }

    @PostMapping()
    public ResponseEntity<GenericResponse<?>> scanImage () throws IOException {
        imageService.scanImageInFolder("D:\\Images\\PreWedding\\Anh cuoi - PTS");
        return ResponseEntity.ok(GenericResponse.success("success"));
    }

    @DeleteMapping()
    public ResponseEntity<GenericResponse<?>> deleteAllImage () {
        imageService.deleteAllImage();
        return ResponseEntity.ok(GenericResponse.success("success"));
    }
}
