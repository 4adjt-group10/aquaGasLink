package com.aquagaslink.product.batch;

import com.aquagaslink.product.controller.dto.ProductFormDto;
import com.aquagaslink.product.infrastructure.ProductRepository;
import com.aquagaslink.product.model.ProductModel;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ProductRepository customerRepository;

    @Bean
    public FlatFileItemReader<ProductModel> reader() {
        FlatFileItemReader<ProductModel> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("product.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);   // skip the first line (header line)
        itemReader.setSkippedLinesCallback(new LineValidator());
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    // How to read the csv file, and how to map the data from the csv file to the Customer object.
    private LineMapper<ProductModel> lineMapper() {
        DefaultLineMapper<ProductModel> lineMapper = new DefaultLineMapper<>();

        // lineTokenizer will extract the data from the csv file.
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(DelimitedLineTokenizer.DELIMITER_COMMA);
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("description","name","price","productCode","stock");

        //will map the extracted value by the lineTokenizer to the target class which is Customer.
        BeanWrapperFieldSetMapper<ProductModel> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ProductModel.class);

        // both objects from previous step need to provide to the line mapper.
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public ProductItemProcessor processor() {
        return new ProductItemProcessor();
    }

    @Bean
    public RepositoryItemWriter<ProductModel> writer() {
        RepositoryItemWriter<ProductModel> writer = new RepositoryItemWriter<>();
        // here we're telling our CustomerRepository to use customerRepository.save method
        // to write the information or the csv data to the Database.
        writer.setRepository(customerRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1() {
        return new StepBuilder("csv-step", jobRepository)
                .<ProductModel, ProductModel>chunk(10, transactionManager)
                .reader(reader())
                .processor((ItemProcessor<? super ProductModel, ? extends ProductModel>) processor())
                .writer(writer())
                .faultTolerant()
                //.skipLimit(5)  // skip up to 5 rows mistakes
                //.skip(NumberFormatException.class)  // if you find any NumberFormatException just skip that row rather than roll back it (restore (a database) to a previously defined state) and process further rows.
                //.noSkip(IllegalArgumentException.class)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job runJob() {
        return new JobBuilder("importCustomers", jobRepository)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }


}