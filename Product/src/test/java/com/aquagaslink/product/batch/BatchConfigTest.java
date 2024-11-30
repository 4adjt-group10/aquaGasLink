package com.aquagaslink.product.batch;

import com.aquagaslink.product.controller.BatchController;
import com.aquagaslink.product.infrastructure.ProductRepository;
import com.aquagaslink.product.model.ProductModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchConfigTest {

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private BatchConfig batchConfig;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        batchConfig = new BatchConfig(transactionManager,jobRepository,productRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void testReader() {
        FlatFileItemReader<ProductModel> reader = batchConfig.reader();
        assertNotNull(reader);
    }


    @Test
    void testProcessor() {
        ProductItemProcessor processor = batchConfig.processor();
        assertNotNull(processor);
    }

    @Test
    void testWriter() {
        RepositoryItemWriter<ProductModel> writer = batchConfig.writer();
        assertNotNull(writer);
    }

    @Test
    void testStep1() {
        Step step = batchConfig.step1();
        assertNotNull(step);
    }

    @Test
    void testRunJob() {
        Job job = batchConfig.runJob();
        assertNotNull(job);
    }

    @Test
    void testTaskExecutor() {
        TaskExecutor taskExecutor = batchConfig.taskExecutor();
        assertNotNull(taskExecutor);
    }
}
