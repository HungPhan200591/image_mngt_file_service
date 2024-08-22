package lazy.demo.image_mngt_file_service.resolver;

import lazy.demo.image_mngt_file_service.model.Image;
import lazy.demo.image_mngt_file_service.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class ImageResolver {

    @Autowired
    private ImageService imageService;

    @QueryMapping
    public List<Image> allImages() {
        return imageService.getAllImages();
    }

    @QueryMapping
    public Image getImage(@Argument UUID id) {
        return imageService.getImageById(id);
    }

    @MutationMapping
    public Image saveImage(@Argument Image image) {
        return imageService.saveImage(image);
    }

    @MutationMapping
    public Image updateImage(@Argument UUID id, @Argument Image image) {
        return imageService.updateImage(id, image);
    }

    @MutationMapping
    public String deleteImage(@Argument UUID id) {
        imageService.deleteImage(id);
        return "Image deleted successfully";
    }
}
