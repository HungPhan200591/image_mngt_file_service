package lazy.demo.image_mngt_file_service.controller;

import jakarta.websocket.server.PathParam;
import lazy.demo.image_mngt_file_service.csv.CsvToDbService;
import lazy.demo.image_mngt_file_service.csv.CsvToDbThreadService;
import lazy.demo.image_mngt_file_service.dto.resp.GenericResponse;
import lazy.demo.image_mngt_file_service.dto.resp.ImageFileNameCount;
import lazy.demo.image_mngt_file_service.model.Image;
import lazy.demo.image_mngt_file_service.service.ImageService;
import lazy.demo.image_mngt_file_service.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file/image")
public class ImageController {

    private final ImageService imageService;

    private final CsvToDbService csvToDbService;
    private final CsvToDbThreadService csvToDbThreadService;

    @GetMapping()
    public ResponseEntity<GenericResponse<?>> getAllImages() {
        return ResponseEntity.ok(GenericResponse.success(imageService.getAllImages()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<?>> getImageById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(GenericResponse.success(imageService.getImage
                (id)));
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

    @GetMapping("/upload-csv")
    public ResponseEntity<GenericResponse<?>> uploadCsv() {
        csvToDbService.insertCsvDataToDb();
        return ResponseEntity.ok(GenericResponse.success("Đã upload và chèn dữ liệu từ file CSV vào MongoDB."));
    }

    @GetMapping("/upload-csv-thread")
    public ResponseEntity<GenericResponse<?>> uploadCsvThread() {
        csvToDbThreadService.insertCsvDataToDb();
        return ResponseEntity.ok(GenericResponse.success("Đã upload và chèn dữ liệu từ file CSV vào MongoDB bằng Thread."));
    }

    @GetMapping("/count")
    public ResponseEntity<GenericResponse<?>> countImage() {

        long count = imageService.countImage();

        return ResponseEntity.ok(GenericResponse.success(count));
    }

    @GetMapping("/count-first-letter")
    public ResponseEntity<GenericResponse<?>> countImageByFirstLetter() {

        List<ImageFileNameCount> count = imageService.countImageByFirstLetter();

        return ResponseEntity.ok(GenericResponse.success(count));
    }

    @GetMapping("/count-images-starting-with")
    public ResponseEntity<GenericResponse<?>> countImagesStartingWith(@RequestParam char letter) {
        long count =  imageService.countImagesStartingWith(letter);
        return ResponseEntity.ok(GenericResponse.success(count));
    }

    @GetMapping("/count-images-with-width-less-than")
    public ResponseEntity<GenericResponse<?>> countImagesWithWidthLessThan(@RequestParam int width) {
        long count = imageService.countImagesWithWidthLessThan(width);
        return ResponseEntity.ok(GenericResponse.success(count));
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<GenericResponse<?>> getAllImageByUser(@PathVariable(value = "user_id") Long userId,
                                                                @RequestParam(name = "page_no", required = false) Integer pageNo,
                                                                @RequestParam(name = "page_size", required = false) Integer pageSize,
                                                                @RequestParam(name = "sort_by", required = false) String sortBy) {

        Pageable pageable = PaginationUtil.createPageable(pageNo, pageSize, sortBy);

        Page<Image> images = imageService.getAllImageByUser(userId, pageable);
        return ResponseEntity.ok(GenericResponse.success(images));
    }
}
