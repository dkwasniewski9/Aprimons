package com.dkwasniewski.aprimons;

import com.dkwasniewski.aprimons.repository.PokeballRepository;
import com.dkwasniewski.aprimons.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class AprimonsApplication {

    @Autowired
    PokemonRepository pokemonRepository;
    @Autowired
    PokeballRepository pokeballRepository;
    public static void main(String[] args) {
        SpringApplication.run(AprimonsApplication.class, args);
    }

}
