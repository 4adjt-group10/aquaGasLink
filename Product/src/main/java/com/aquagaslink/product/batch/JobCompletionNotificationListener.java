package com.aquagaslink.product.batch;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Override
    public void beforeJob(@NotNull JobExecution jobExecution) {
        // Não é necessário fazer nada antes do job
    }

    @Override
    public void afterJob(@NotNull JobExecution jobExecution) {
        File file = new File("product.csv");
        if (file.exists()) {
            try {
                Files.deleteIfExists(file.toPath());
                logger.info("CSV file deleted successfully.");
            } catch (IOException e) {
                logger.error("Error deleting the CSV file", e);
            }
        }
    }
}
