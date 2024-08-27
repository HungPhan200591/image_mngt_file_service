package lazy.demo.image_mngt_file_service.service;

import lazy.demo.image_mngt_file_service.dto.resp.ImageFileNameCount;
import lazy.demo.image_mngt_file_service.model.Image;
import lazy.demo.image_mngt_file_service.repository.ImageRepository;
import lazy.demo.image_mngt_file_service.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    private final MongoTemplate mongoTemplate;

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Image getImage(UUID id) {
        return imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    public Image getImageById(UUID id) {
        return imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    public void deleteImage(UUID id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        imageRepository.delete(image);
    }

    public void scanImageInFolder(String folderPath) throws IOException {
        File directoryPath = new File(folderPath);

        File[] files = directoryPath.listFiles();

        if (Objects.isNull(files)) {
            return;
        }

        List<Image> images = new ArrayList<>();

        for (File file : files) {

            if (file.isDirectory()) {
                continue;
            }

            String fileName = file.getName();

            String baseName = FilenameUtils.getBaseName(fileName);
            String extension = FilenameUtils.getExtension(fileName);
            if (!(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png"))) {
                continue;
            }

            Image image = getImage(file);

            image.setImageFileName(baseName);
            image.setMimeType(extension);
            image.setUrl(file.getPath());
            image.setUploadedAt(LocalDateTime.now());
            image.setSize(file.length());

            images.add(image);
        }

        imageRepository.saveAll(images);
    }

    public Image getImage (File file) throws IOException {

        Image image = new Image();
        image.set_imageId(UUID.randomUUID());
        try (ImageInputStream stream = ImageIO.createImageInputStream(file)) {
            ImageReader reader = ImageIO.getImageReaders(stream).next();
            reader.setInput(stream);

            int width = reader.getWidth(0);
            int height = reader.getHeight(0);

            reader.dispose();

            double imageRatio = (double) Math.round((double) width / height * 100) / 100;

            image.setImageWidth(width);
            image.setImageHeight(height);
            image.setImageRatio(imageRatio);
        }

        return image;
    }

    public void deleteAllImage() {
        imageRepository.deleteAll();
    }

    public void uploadImage() {

    }

    public long countImage() {
        return  imageRepository.countBy();
//        return mongoTemplate.getCollection("image").countDocuments();
    }

    public List<ImageFileNameCount> countImageByFirstLetter() {
        return  imageRepository.countImagesByFirstLetter();
//        AggregationOptions options = AggregationOptions.builder()
//                .allowDiskUse(true)  // Sử dụng đĩa nếu RAM không đủ
//                .build();
//
//
//        Aggregation aggregation = Aggregation.newAggregation(
//                // Bước $project
//                Aggregation.project().andExpression("substrCP(image_file_name, 0, 1)").as("firstLetter"),
//
//                // Bước $group
//                Aggregation.group("firstLetter").count().as("count"),
//                // Bước $sort
//                Aggregation.sort(Sort.by(Sort.Direction.ASC, "_id"))
//        ).withOptions(options);
//
//        AggregationResults<ImageFileNameCount> result = mongoTemplate.aggregate(aggregation, "image", ImageFileNameCount.class);
//        return result.getMappedResults();
    }

    public long countImagesStartingWith(char letter) {
        return imageRepository.countByFileNameStartingWith(letter);
    }

    public long countImagesWithWidthLessThan(int width) {
        return imageRepository.countByImageWidthLessThan(width);
    }

    public Page<Image> getAllImageByUser(Long userId, Pageable pageable) {

        return imageRepository.findByUserId(userId, pageable);
    }

}