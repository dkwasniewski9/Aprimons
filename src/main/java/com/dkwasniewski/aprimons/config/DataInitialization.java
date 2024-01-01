package com.dkwasniewski.aprimons.config;

import com.dkwasniewski.aprimons.service.CsvImportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DataInitialization implements CommandLineRunner {

    private final CsvImportService importService;

    public DataInitialization(CsvImportService importService) {
        this.importService = importService;
    }

    @Override
    public void run(String... args) {
        try {
            importService.importDataFromCsv("src/main/resources/config/LegalityChart.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
