package com.aquagaslink.product.controller;

import com.aquagaslink.product.batch.JobCompletionNotificationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchController {
    private static final Logger logger = LoggerFactory.getLogger(BatchController.class);

    final JobLauncher jobLauncher;
    final Job jobProduct;

    public BatchController(JobLauncher jobLauncher, Job jobProduct) {
        this.jobLauncher = jobLauncher;
        this.jobProduct = jobProduct;
    }


    @GetMapping("/start")
    public String startBatch() {
        try {
            jobLauncher
                    .run(jobProduct, new JobParametersBuilder()
                            .addLong("startAt", System.currentTimeMillis())
                            .toJobParameters());
            return "Batch process started!";
        } catch (Exception e) {
            logger.error("Failed to start batch process.", e);
            return "Failed to start batch process.";
        }
    }
}