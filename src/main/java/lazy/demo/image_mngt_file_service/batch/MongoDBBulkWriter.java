//package lazy.demo.image_mngt_file_service.batch;
//
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.BulkOperations;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MongoDBBulkWriter<T> implements ItemWriter<T> {
//
//    private final MongoTemplate mongoTemplate;
//    private final Class<T> entityClass;
//
//    @Autowired
//    public MongoDBBulkWriter(MongoTemplate mongoTemplate, Class<T> entityClass) {
//        this.mongoTemplate = mongoTemplate;
//        this.entityClass = entityClass;
//    }
//
//
//    @Override
//    public void write(Chunk<? extends T> chunk) throws Exception {
//        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, entityClass);
//        bulkOps.insert(chunk.getItems());
//        bulkOps.execute();
//    }
//}
