package com.dkwasniewski.aprimons;

import com.dkwasniewski.aprimons.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@AllArgsConstructor
public class AprimonsApplication {

    PokemonRepository pokemonRepository;
    PokeballRepository pokeballRepository;
    UserRepository userRepository;
    UserCollectionRepository userCollectionRepository;
    MailTokenRepository mailTokenRepository;

    public static void main(String[] args) {
        SpringApplication.run(AprimonsApplication.class, args);
    }

}
