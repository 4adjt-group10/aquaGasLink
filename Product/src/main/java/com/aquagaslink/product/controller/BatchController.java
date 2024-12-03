package com.aquagaslink.product.controller;

import com.aquagaslink.product.service.JobBathService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/batch")
public class BatchController {

    final JobBathService jobBathService;

    public BatchController(JobBathService jobBathService) {
        this.jobBathService = jobBathService;
    }

    @PostMapping("/upload_csv")
    @Operation(summary = "Upload and save CSV file")
    public String uploadCsv(@RequestParam("file") MultipartFile file) {
       return jobBathService.saveFile(file);
    }

    @GetMapping("/start")
    @Operation(summary = "Start batch service")
    public String startBatch() {
        return jobBathService.startBath();
    }
}