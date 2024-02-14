package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.UserCollection;
import com.dkwasniewski.aprimons.repository.UserCollectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@AllArgsConstructor
public class UserCollectionService {
    private UserCollectionRepository userCollectionRepository;

    public void saveUserCollection(UserCollection userCollection) {
        userCollectionRepository.save(userCollection);
    }

    public UserCollection getUserCollection(String userId) {
        UserCollection userCollection = userCollectionRepository.getUserCollectionByUserId(userId);
        if (ObjectUtils.isEmpty(userCollection)) {

            userCollection = userCollectionRepository.save(new UserCollection(userId));
        }
        return userCollection;
    }
}
