package lazy.demo.image_mngt_file_service.service;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UpdateService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final int BATCH_SIZE = 10000;  // Kích thước của mỗi batch
    private static final int TOTAL_THREADS = 12; // Số lượng thread

    public void updateData() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("image");

        // Tạo ExecutorService với số lượng thread được xác định
        ExecutorService executorService = Executors.newFixedThreadPool(TOTAL_THREADS);

        // Chia 10 triệu bản ghi thành các batch
        long totalDocuments = collection.countDocuments();
        long processedDocuments = 0;

        while (processedDocuments < totalDocuments) {
            List<Document> documents = collection.find()
                    .skip((int) processedDocuments)
                    .limit(BATCH_SIZE)
                    .into(new ArrayList<>());

            // Tạo các thread để xử lý các batch này
            executorService.submit(() -> {
                List<WriteModel<Document>> bulkOperations = new ArrayList<>();

                for (Document doc : documents) {
                    int randomUserId = (int) (Math.random() * 100) + 1;

                    // Tạo một cập nhật cho mỗi document
                    WriteModel<Document> updateOne = new UpdateOneModel<>(
                            new Document("_id", doc.get("_id")),
                            new Document("$set", new Document("user_id", randomUserId))
                    );

                    bulkOperations.add(updateOne);
                }

                // Thực hiện bulk update
                if (!bulkOperations.isEmpty()) {
                    BulkWriteResult result = collection.bulkWrite(bulkOperations, new BulkWriteOptions().ordered(false));
                    System.out.println("Updated " + result.getModifiedCount() + " documents");
                }
            });

            processedDocuments += BATCH_SIZE;
        }

        // Shutdown ExecutorService sau khi tất cả các batch đã được xử lý
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // Chờ cho tất cả các thread hoàn thành
        }
    }
}
