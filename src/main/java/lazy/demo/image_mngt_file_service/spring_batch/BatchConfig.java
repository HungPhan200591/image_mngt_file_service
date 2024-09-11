//package lazy.demo.image_mngt_file_service.spring_batch;
//
//import lazy.demo.image_mngt_file_service.model.Image;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.data.MongoItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.transaction.PlatformTransactionManager;
//import javax.sql.DataSource;
//
//@Configuration
//@EnableBatchProcessing
//public class BatchConfig {
//
//    private final MongoTemplate mongoTemplate;
//
//    public BatchConfig(MongoTemplate mongoTemplate) {
//        this.mongoTemplate = mongoTemplate;
//    }
//
//    @Bean
//    public JobRepository jobRepositoryjobRepository(PlatformTransactionManager transactionManager) throws Exception {
//        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
//        factory.setTransactionManager(transactionManager);
//        factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
//        factory.setTablePrefix("BATCH_");
//        factory.setDataSource(dataSource());  // Cần tạo một DataSource nhưng không dùng thực tế
//        factory.afterPropertiesSet();
//        return factory.getObject();
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        // Tạo DataSource giả lập, nhưng không thực sự sử dụng (tùy chọn thay thế sẽ không sử dụng).
//        // Có thể sử dụng H2 hoặc các cấu hình khác nếu cần
//        return new org.springframework.jdbc.datasource.DriverManagerDataSource();
//    }
//
//    @Bean
//    public FlatFileItemReader<Image> reader() {
//        return new FlatFileItemReaderBuilder<Image>()
//                .name("imageItemReader")
//                .resource(new ClassPathResource("images.csv"))
//                .delimited()
//                .delimiter(DelimitedLineTokenizer.DELIMITER_COMMA)
//                .names("userId", "url", "imageFileName", "uploadedAt", "mimeType", "imageType",
//                        "imageWidth", "imageHeight", "imageRatio", "imageRotation",
//                        "imageScale", "imageArtist", "size")
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
//                    setTargetType(Image.class);
//                }})
//                .build();
//    }
//
//    @Bean
//    public MongoItemWriter<Image> writer() {
//        MongoItemWriter<Image> writer = new MongoItemWriter<>();
//        writer.setTemplate(mongoTemplate);
//        writer.setCollection("image");
//        return writer;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new ResourcelessTransactionManager();
//    }
//
//    @Bean
//    public Job insertJob(JobRepository jobRepository) {
//        return new JobBuilder("insertJob", jobRepository)
//                .start(insertStep(jobRepository))
//                .build();
//    }
//
//    @Bean
//    public Step insertStep(JobRepository jobRepository) {
//        return new StepBuilder("insertStep", jobRepository)
//                .<Image, Image>chunk(1000, transactionManager())
//                .reader(reader())
//                .writer(writer())
//                .build();
//    }
//}
