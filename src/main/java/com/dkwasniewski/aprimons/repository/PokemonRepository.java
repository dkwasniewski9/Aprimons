package com.dkwasniewski.aprimons.repository;

import com.dkwasniewski.aprimons.model.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PokemonRepository extends MongoRepository<Pokemon, String> {
    Pokemon findByDexNumber(int dexNumber);
}
