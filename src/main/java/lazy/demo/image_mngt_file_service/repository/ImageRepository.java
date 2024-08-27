package lazy.demo.image_mngt_file_service.repository;

import lazy.demo.image_mngt_file_service.dto.resp.ImageFileNameCount;
import lazy.demo.image_mngt_file_service.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface ImageRepository extends MongoRepository<Image, UUID> {
    long countBy();

    @Aggregation(pipeline = {
            "{ '$project': { 'firstLetter': { '$substr': [ '$image_file_name', 0, 1 ] } } }",
            "{ '$group': { '_id': '$firstLetter', 'count': { '$sum': 1 } } }",
            "{ '$sort': { '_id': 1 } }"
    })
    List<ImageFileNameCount> countImagesByFirstLetter();

    @Query(value = "{ 'image_file_name': { $regex: '^?0', $options: 'i' } }", count = true)
    long countByFileNameStartingWith(char letter);

    @Query(value = "{ 'image_width': { $lt: ?0 } }", count = true)
    long countByImageWidthLessThan(int width);

    Page<Image> findByUserId(Long userId, Pageable pageable);
    // Tìm kiếm bắt đầu với `searchValue`, không phân biệt chữ hoa chữ thường, và giới hạn kết quả trả về 10 bản ghi đầu tiên.
    @Query("{ 'imageFileName': { $regex: '^?0', $options: 'i' } }")
    List<Image> findTop10ByImageFileNameStartingWith(String searchValue, Pageable pageable);

}
