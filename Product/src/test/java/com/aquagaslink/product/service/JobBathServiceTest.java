package com.aquagaslink.product.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobBathServiceTest {

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job jobProduct;

    @Mock
    private MultipartFile multipartFile;

    private JobBathService jobBathService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        jobBathService = new JobBathService(jobLauncher, jobProduct);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void startBath_success() throws Exception {
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenReturn(mock(JobExecution.class));
        String result = jobBathService.startBath();
        assertEquals("Batch process started!", result);
    }


    @Test
    void startBath_failure() throws Exception {
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenThrow(new RuntimeException("Failed to start"));
        String result = jobBathService.startBath();
        assertEquals("Failed to start batch process.", result);
    }

    @Test
    void saveFile_noFile() {
        when(multipartFile.isEmpty()).thenReturn(true);
        assertThatThrownBy(() -> jobBathService.saveFile(multipartFile))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Nenhum arquivo foi enviado");
    }

    @Test
    void saveFile_invalidFile() {
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("test.txt");
        assertThatThrownBy(() -> jobBathService.saveFile(multipartFile))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Arquivo inválido");
    }

    @Test
    void saveFile_success() throws Exception {
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("test.csv");
        when(multipartFile.getBytes()).thenReturn("name,description\nTest Product,Description".getBytes());
        Path path = Paths.get("test.csv");
        Files.write(path, multipartFile.getBytes());
        String result = jobBathService.saveFile(multipartFile);
        assertEquals("ok", result);
        File file = new File("test.csv");
        if (file.delete()) {
            LoggerFactory.getLogger(JobBathService.class).info("Arquivo deletado com sucesso");
        } else {
            LoggerFactory.getLogger(JobBathService.class).warn("Falha ao deletar o arquivo");
        }
    }

    @Test
    void saveFile_failure() throws Exception {
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("test.csv");
        when(multipartFile.getBytes()).thenThrow(new IOException("IO Exception"));

        assertThatThrownBy(() -> jobBathService.saveFile(multipartFile))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Erro ao salvar o arquivo");
    }
}
