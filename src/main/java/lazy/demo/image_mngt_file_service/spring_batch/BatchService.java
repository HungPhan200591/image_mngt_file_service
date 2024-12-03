//package lazy.demo.image_mngt_file_service.spring_batch;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BatchService {
//    @Autowired
//    private JobLauncher jobLauncher;
//
//    @Autowired
//    private Job insertJob;
//
//    public void runBatchJob() throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLong("startAt", System.currentTimeMillis())
//                .toJobParameters();
//        jobLauncher.run(insertJob, jobParameters);
//    }
//}
