package com.aquagaslink.product.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController @RequestMapping("/batch")
public class BatchController {
    @Autowired private JobLauncher jobLauncher;
    @Autowired private Job Job;

    @GetMapping("/start") public String startBatch() {
        try {
            jobLauncher
                    .run(Job, new JobParametersBuilder()
                            .addLong("startAt", System.currentTimeMillis())
                            .toJobParameters());
            return "Batch process started!";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Failed to start batch process.";
        }
    }
}