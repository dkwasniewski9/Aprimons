package com.dkwasniewski.aprimons.repository;

import com.dkwasniewski.aprimons.model.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PokemonRepository extends MongoRepository<Pokemon, String> {

    Pokemon findByName(String name);
}
