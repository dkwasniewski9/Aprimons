package com.dkwasniewski.aprimons.repository;

import com.dkwasniewski.aprimons.model.Pokeball;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PokeballRepository extends MongoRepository<Pokeball, String> {
    Pokeball findByName(String name);
}
