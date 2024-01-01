package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.Pokeball;
import com.dkwasniewski.aprimons.model.Pokemon;
import com.dkwasniewski.aprimons.repository.PokeballRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PokeballService {
    @Autowired
    private PokeballRepository pokeballRepository;
    public void savePokeball(Pokeball pokeball){
        pokeballRepository.save(pokeball);
    }

    public Pokeball findPokeballByName(String name) { return pokeballRepository.findByName(name); }
}
