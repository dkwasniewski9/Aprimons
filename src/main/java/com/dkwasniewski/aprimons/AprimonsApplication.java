package com.dkwasniewski.aprimons;

import com.dkwasniewski.aprimons.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class AprimonsApplication {

    @Autowired
    PokemonRepository pokemonRepository;
    @Autowired
    PokeballRepository pokeballRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserCollectionRepository userCollectionRepository;
    @Autowired
    MailTokenRepository mailTokenRepository;
    public static void main(String[] args) {
        SpringApplication.run(AprimonsApplication.class, args);
    }

}
