package com.aquagaslink.product.controller;

import com.aquagaslink.product.service.JobBathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/batch")
public class BatchController {

    private static final Logger logger = LoggerFactory.getLogger(BatchController.class);

    final JobBathService jobBathService;

    public BatchController(JobBathService jobBathService) {
        this.jobBathService = jobBathService;
    }

    @PostMapping("/upload_csv")
    public String uploadCsv(@RequestParam("file") MultipartFile file) {
       return jobBathService.saveFile(file);
    }

    @GetMapping("/start")
    public String startBatch() {
        return jobBathService.startBath();
    }
}