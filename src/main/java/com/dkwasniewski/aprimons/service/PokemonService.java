package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.Pokemon;
import com.dkwasniewski.aprimons.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;
    public void savePokemon(Pokemon pokemon){
        pokemonRepository.save(pokemon);
    }
}
