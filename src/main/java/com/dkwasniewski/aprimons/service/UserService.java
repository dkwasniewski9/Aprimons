package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.Pokeball;
import com.dkwasniewski.aprimons.model.User;
import com.dkwasniewski.aprimons.repository.PokeballRepository;
import com.dkwasniewski.aprimons.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public void saveUser(User user){
        userRepository.save(user);
    }

    public User find(String name) { return userRepository.findUserByUsername(name); }
}
