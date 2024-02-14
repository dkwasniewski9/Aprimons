package com.dkwasniewski.aprimons.repository;

import com.dkwasniewski.aprimons.model.UserCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserCollectionRepository extends MongoRepository<UserCollection, String> {
    UserCollection getUserCollectionByUserId(String userId);

}
