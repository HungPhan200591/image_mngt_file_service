//package lazy.demo.image_mngt_file_service.spring_batch;
//
//import lazy.demo.image_mngt_file_service.model.Image;
//import org.springframework.batch.item.ItemProcessor;
//
//import java.util.UUID;
//
//public class ImageProcessor implements ItemProcessor<Image, Image> {
//
//    @Override
//    public Image process(Image item) throws Exception {
//        item.set_imageId(UUID.randomUUID()); // Tạo UUID mới cho mỗi image
//        return item;
//    }
//}package lazy.demo.image_mngt_file_service.spring_batch;
//
//import lazy.demo.image_mngt_file_service.model.Image;
//import org.springframework.batch.item.ItemProcessor;
//
//import java.util.UUID;
//
//public class ImageProcessor implements ItemProcessor<Image, Image> {
//
//    @Override
//    public Image process(Image item) throws Exception {
//        item.set_imageId(UUID.randomUUID()); // Tạo UUID mới cho mỗi image
//        return item;
//    }
//}