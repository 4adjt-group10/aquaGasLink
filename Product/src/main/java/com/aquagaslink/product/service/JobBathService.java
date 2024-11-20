package com.aquagaslink.product.service;

import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class JobBathService {
    private static final Logger logger = LoggerFactory.getLogger(JobBathService.class);
    private static final String ARQUIVO_INVALIDO = "Arquivo inválido";
    private static final String SEM_ARQUIVO = "Nenhum arquivo foi enviado";
    private static final String ERRO_AO_SALVAR = "Erro ao salvar o arquivo";
    private static final String SUCESSO = "ok";

    final JobLauncher jobLauncher;
    final Job jobProduct;

    public JobBathService(JobLauncher jobLauncher, Job jobProduct) {
        this.jobLauncher = jobLauncher;
        this.jobProduct = jobProduct;
    }

    public String startBath() {
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

    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new EntityNotFoundException(SEM_ARQUIVO);
        }
        if (!file.getOriginalFilename().endsWith(".csv")) {
            throw new EntityNotFoundException(ARQUIVO_INVALIDO);
        }
        try { // Salvar o arquivo na raiz do projeto
            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, file.getBytes()); // Ler o arquivo CSV
            Reader reader = new FileReader(new File(file.getOriginalFilename()));
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            for (CSVRecord record : records) { // Aqui você pode realizar operações adicionais com cada registro do CSV
                logger.info(record.toString());
            }
            return SUCESSO;
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException(ERRO_AO_SALVAR);
        }
    }
}
