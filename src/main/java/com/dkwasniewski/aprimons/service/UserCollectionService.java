package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.Pokemon;
import com.dkwasniewski.aprimons.model.UserCollection;
import com.dkwasniewski.aprimons.repository.PokemonRepository;
import com.dkwasniewski.aprimons.repository.UserCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCollectionService {
    @Autowired
    private UserCollectionRepository userCollectionRepository;
    public void saveUserCollection(UserCollection userCollection){
        userCollectionRepository.save(userCollection);
    }
}
