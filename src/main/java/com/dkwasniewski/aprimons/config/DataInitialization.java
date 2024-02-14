package com.dkwasniewski.aprimons.config;

import com.dkwasniewski.aprimons.model.OwnedPokemon;
import com.dkwasniewski.aprimons.model.User;
import com.dkwasniewski.aprimons.model.UserCollection;
import com.dkwasniewski.aprimons.repository.PokeballRepository;
import com.dkwasniewski.aprimons.repository.PokemonRepository;
import com.dkwasniewski.aprimons.repository.UserCollectionRepository;
import com.dkwasniewski.aprimons.service.CsvImportService;
import com.dkwasniewski.aprimons.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import util.Role;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInitialization implements CommandLineRunner {

    private final CsvImportService importService;
    private final UserService userService;
    private final PokeballRepository pokeballRepository;
    private final PasswordEncoder passwordEncoder;
    private final PokemonRepository pokemonRepository;
    private final UserCollectionRepository userCollectionRepository;

    @Override
    public void run(String... args) {
        if (pokeballRepository.count() != 0) return;
        try {
            importService.importDataFromCsv("src/main/resources/config/LegalityChart.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setUsername("pingwin");
        user.setPassword(passwordEncoder.encode("zaq1@WSX"));
        user.setEmail("pingwin296@gmail.com");
        user.setRole(Role.ADMIN);
        user.setActive(true);
        userService.saveUser(user);

        UserCollection userCollection = new UserCollection(user.getId());
        List<OwnedPokemon> ownedPokemonList = new ArrayList<>();
        List<String> pokeballIdList = new ArrayList<>();
        pokeballIdList.add(pokeballRepository.findByName("Safari").getId());
        ownedPokemonList.add(new OwnedPokemon(pokemonRepository.findByDexNumber(1).getId(), pokeballIdList));
        userCollection.setOwnedPokemonList(ownedPokemonList);
        userCollectionRepository.save(userCollection);
    }
}
