package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.Pokeball;
import com.dkwasniewski.aprimons.repository.PokeballRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PokeballService {
    private PokeballRepository pokeballRepository;

    public List<Pokeball> getAllPokeball() {
        return pokeballRepository.findAll();
    }
}
