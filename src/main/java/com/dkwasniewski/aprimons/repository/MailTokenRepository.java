package com.dkwasniewski.aprimons.repository;

import com.dkwasniewski.aprimons.model.MailToken;
import com.dkwasniewski.aprimons.model.Pokeball;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MailTokenRepository extends MongoRepository<MailToken, String> {
    MailToken findMailTokenByUUID(String UUID);
}
