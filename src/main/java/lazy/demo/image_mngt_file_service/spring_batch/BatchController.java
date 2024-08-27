//package lazy.demo.image_mngt_file_service.spring_batch;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController("/file/batch")
//public class BatchController {
//
//    @Autowired
//    private BatchService batchService;
//
//    @PostMapping("/run-batch")
//    public ResponseEntity<String> runBatchJob() {
//        try {
//            batchService.runBatchJob();
//            return ResponseEntity.ok("Batch job started successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Failed to start batch job: " + e.getMessage());
//        }
//    }
//}
