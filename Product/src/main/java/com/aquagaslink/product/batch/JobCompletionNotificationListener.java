package com.aquagaslink.product.batch;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    @Override
    public void beforeJob(@NotNull JobExecution jobExecution) {
        // Não é necessário fazer nada antes do job
    }

    @Override
    public void afterJob(@NotNull JobExecution jobExecution) {
        File file = new File("product.csv");
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("CSV file deleted successfully.");
            } else {
                System.out.println("Failed to delete CSV file.");
            }
        }
    }
}
