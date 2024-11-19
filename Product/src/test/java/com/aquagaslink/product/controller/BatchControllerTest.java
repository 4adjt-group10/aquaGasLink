//package com.aquagaslink.product.controller;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.launch.JobLauncher;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class BatchControllerTest {
//
//    @Mock
//    private JobLauncher jobLauncher;
//
//    @Mock
//    private Job jobProduct;
//
//    @InjectMocks
//    private BatchController controller;
//
//    @Test
//    public void testStartBatchSuccess() throws Exception {
//        // Given
//        JobParameters jobParameters = new JobParameters();
//
//        // When
//        when(jobLauncher.run(jobProduct, jobParameters)).thenReturn(null);
//        String result = controller.startBatch();
//
//        // Then
//        verify(jobLauncher).run(jobProduct, jobParameters);
//        Assertions.assertEquals("Batch process started!", result);
//    }
//}
