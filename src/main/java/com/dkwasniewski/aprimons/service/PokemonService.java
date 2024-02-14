package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.Pokemon;
import com.dkwasniewski.aprimons.repository.PokemonRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PokemonService {
    private PokemonRepository pokemonRepository;
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }

    public Page<Pokemon> getAllPokemonPages(PageRequest pageRequest) {
        return pokemonRepository.findAll(pageRequest);
    }

}
