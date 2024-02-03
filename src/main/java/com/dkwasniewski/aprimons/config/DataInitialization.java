package com.dkwasniewski.aprimons.config;

import com.dkwasniewski.aprimons.model.User;
import com.dkwasniewski.aprimons.repository.PokeballRepository;
import com.dkwasniewski.aprimons.service.CsvImportService;
import com.dkwasniewski.aprimons.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import util.Role;

import java.io.IOException;

@Component
public class DataInitialization implements CommandLineRunner {

    private final CsvImportService importService;
    private final UserService userService;
    private final PokeballRepository pokeballRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitialization(CsvImportService importService, UserService userService, PokeballRepository pokeballRepository, PasswordEncoder passwordEncoder) {
        this.importService = importService;
        this.userService = userService;
        this.pokeballRepository = pokeballRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if(pokeballRepository.count() != 0) return;
        try {
            importService.importDataFromCsv("src/main/resources/config/LegalityChart.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setUsername("pingwin");
        user.setPassword(passwordEncoder.encode("zaq1@WSX"));
        user.setEmail("pingwin296@gmail.com");
        user.setRole(Role.admin);
        userService.saveUser(user);
    }
}
