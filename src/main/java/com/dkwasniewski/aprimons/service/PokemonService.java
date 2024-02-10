package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.Pokemon;
import com.dkwasniewski.aprimons.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;
    public void savePokemon(Pokemon pokemon){
        pokemonRepository.save(pokemon);
    }
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }
    public Page<Pokemon> getAllPokemonPages(PageRequest pageRequest){
        return pokemonRepository.findAll(pageRequest);
    }

}
