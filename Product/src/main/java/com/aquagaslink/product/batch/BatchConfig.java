package com.aquagaslink.product.batch;

import com.aquagaslink.product.infrastructure.ProductRepository;
import com.aquagaslink.product.model.ProductModel;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    ProductRepository productRepository;

    @Bean
    public FlatFileItemReader<ProductModel> reader() {
        return new FlatFileItemReaderBuilder<ProductModel>()
                .name("productItemReader")
                .resource(new FileSystemResource("product.csv"))
                .delimited()
                .names(new String[]{"name", "productCode", "description","price","stock"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<ProductModel>() {{
                    setTargetType(ProductModel.class);
                }})
                .build();
    }

    @Bean
    public ProductItemProcessor processor() {
        return new ProductItemProcessor();
    }

    @Bean
    public RepositoryItemWriter<ProductModel> writer() {
        return new RepositoryItemWriterBuilder<ProductModel>()
                .repository(productRepository)
                .methodName("save") .build();
    }

    @Bean
    public Job importProductJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("importProductJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, FlatFileItemReader<ProductModel> reader,
                      ProductItemProcessor processor, RepositoryItemWriter<ProductModel> writer) {
        return new StepBuilder("step1",jobRepository)
                .<ProductModel, ProductModel>chunk(10,platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public JobCompletionNotificationListener listener() {
        return new JobCompletionNotificationListener();
    }
}
