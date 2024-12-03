//package lazy.demo.image_mngt_file_service.batch;
//
//import lazy.demo.image_mngt_file_service.model.Image;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.BulkOperations;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class MongoDBBulkInsertService {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    public void bulkInsert(List<Image> entities) {
//        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Image.class);
//        bulkOps.insert(entities);
//        bulkOps.execute();
//    }
//}
