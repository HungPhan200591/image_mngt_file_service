//package lazy.demo.image_mngt_file_service.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.core.task.TaskExecutor;
//
//@Configuration
//public class TaskExecutorConfig {
//
//    @Bean("taskExecutor")
//    public TaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(10); // Số luồng cốt lõi luôn sẵn sàng
//        executor.setMaxPoolSize(20); // Số luồng tối đa có thể tạo
//        executor.setQueueCapacity(50); // Dung lượng hàng đợi
//        executor.setThreadNamePrefix("AsyncTask-"); // Tiền tố cho tên các luồng
//        executor.initialize();
//        return executor;
//    }
//}
