//package lazy.demo.image_mngt_file_service.batch;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class BatchController {
//
//    @Autowired
//    private JobLauncher jobLauncher;
//
//    @Autowired
//    private Job importJob;
//
//    @GetMapping("/run-batch")
//    public String runBatchJob() {
//        try {
//            jobLauncher.run(importJob, new JobParameters());
//            return "Batch job has been started.";
//        } catch (Exception e) {
//            return "Error starting batch job: " + e.getMessage();
//        }
//    }
//}
