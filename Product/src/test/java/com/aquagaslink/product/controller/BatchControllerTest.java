package com.aquagaslink.product.controller;

import com.aquagaslink.product.service.JobBathService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchControllerTest {

    @Mock
    private JobBathService jobBathService;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private BatchController batchController;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        batchController = new BatchController(jobBathService);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }


    @Test
    void testUploadCsv() {
        when(jobBathService.saveFile(any(MultipartFile.class))).thenReturn("File uploaded successfully!");

        String response = batchController.uploadCsv(multipartFile);

        assertEquals("File uploaded successfully!", response);
        verify(jobBathService, times(1)).saveFile(any(MultipartFile.class));
    }

    @Test
    void testStartBatch() {
        when(jobBathService.startBath()).thenReturn("Batch process started!");

        String response = batchController.startBatch();

        assertEquals("Batch process started!", response);
        verify(jobBathService, times(1)).startBath();
    }
}
