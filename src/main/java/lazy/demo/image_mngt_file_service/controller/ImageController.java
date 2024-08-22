package lazy.demo.image_mngt_file_service.controller;

import jakarta.websocket.server.PathParam;
import lazy.demo.image_mngt_file_service.dto.resp.GenericResponse;
import lazy.demo.image_mngt_file_service.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file/image")
public class ImageController {

    private final ImageService imageService;

    @GetMapping()
    public ResponseEntity<GenericResponse<?>> getAllImages() {
        return ResponseEntity.ok(GenericResponse.success(imageService.getAllImages()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<?>> getImageById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(GenericResponse.success(imageService.getImage(id)));
    }

    @PostMapping()
    public ResponseEntity<GenericResponse<?>> scanImage () throws IOException {
        System.out.println("scan image");
        imageService.scanImageInFolder("D:\\Images\\PreWedding\\Anh cuoi - PTS");
        return ResponseEntity.ok(GenericResponse.success("success"));
    }

    @DeleteMapping()
    public ResponseEntity<GenericResponse<?>> deleteAllImage () {
        imageService.deleteAllImage();
        return ResponseEntity.ok(GenericResponse.success("success"));
    }

    @PostMapping("/upload")
    public ResponseEntity<GenericResponse<?>> uploadImage() {
        imageService.uploadImage();
        return ResponseEntity.ok(GenericResponse.success("success"));
    }
}
