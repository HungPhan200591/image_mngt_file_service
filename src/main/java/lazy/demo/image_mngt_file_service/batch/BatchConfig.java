//package lazy.demo.image_mngt_file_service.batch;
//
//import lazy.demo.image_mngt_file_service.model.Image;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//
//@Configuration
//@EnableBatchProcessing
//public class BatchConfig {
//
//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
//
//    @Autowired
//    private MongoDBBulkInsertService mongoDBBulkInsertService;
//
//    @Bean
//    public FlatFileItemReader<Image> reader() {
//        return new FlatFileItemReaderBuilder<Image>()
//                .name("csvItemReader")
//                .resource(new FileSystemResource("path/to/your/csvfile.csv"))
//                .delimited()
//                .names(new String[]{"orderId", "orderDate", "customerId", "amount"})
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<Image>() {{
//                    setTargetType(Image.class);
//                }})
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<Image, Image> processor() {
//        return item -> {
//            // Thực hiện các xử lý cần thiết trên đối tượng MyEntity nếu cần
//            return item;
//        };
//    }
//
//    @Bean
//    public ItemWriter<Image> writer() {
//        return new MongoDBBulkWriter(mongoDBBulkInsertService);
//    }
//
//    @Bean
//    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
//        return jobBuilderFactory.get("importUserJob")
//                .incrementer(new RunIdIncrementer())
//                .listener(listener)
//                .flow(step1)
//                .end()
//                .build();
//    }
//
//    @Bean
//    public Step step1(ItemReader<Image> reader,
//                      ItemProcessor<Image, Image> processor,
//                      ItemWriter<Image> writer) {
//        return stepBuilderFactory.get("step1")
//                .<Image, Image>chunk(10000)  // Số lượng bản ghi xử lý trong mỗi batch
//                .reader(reader)
//                .processor(processor)
//                .writer(writer)
//                .build();
//    }
//}
