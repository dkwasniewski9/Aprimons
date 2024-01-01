package com.dkwasniewski.aprimons.repository;

import com.dkwasniewski.aprimons.model.Pokeball;
import com.dkwasniewski.aprimons.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserByUsername(String name);
}
