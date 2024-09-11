//package lazy.demo.image_mngt_file_service.config;
//
//import graphql.schema.Coercing;
//import graphql.schema.GraphQLScalarType;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class LongScalarConfiguration {
//
//    @Bean
//    public GraphQLScalarType longScalar() {
//        return GraphQLScalarType.newScalar()
//                .name("Long")
//                .description("A Long scalar")
//                .coercing(new Coercing<Long, Long>() {
//                    @Override
//                    public Long serialize(Object dataFetcherResult) {
//                        return ((Long) dataFetcherResult);
//                    }
//
//                    @Override
//                    public Long parseValue(Object input) {
//                        return Long.parseLong(input.toString());
//                    }
//
//                    @Override
//                    public Long parseLiteral(Object input) {
//                        return Long.parseLong(input.toString());
//                    }
//                })
//                .build();
//    }
//}
