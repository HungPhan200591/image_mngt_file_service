package lazy.demo.image_mngt_file_service.resolver;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lazy.demo.image_mngt_file_service.model.Image;
import lazy.demo.image_mngt_file_service.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImageResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private ImageService imageService;

    public List<Image> getAllImages() {
        return imageService.getAllImages();
    }

    public Image uploadImage(String userId, String url, String fileName, String mimeType, long size) {
        return imageService.uploadImage(userId, url, fileName, mimeType, size);
    }

    public boolean deleteImage(String id) {
        imageService.deleteImage(id);
        return true;
    }
}
