package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.Pokemon;
import com.dkwasniewski.aprimons.repository.PokemonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PokemonServiceTest {
    @Mock
    private PokemonRepository pokemonRepository;
    @InjectMocks
    private PokemonService pokemonService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    public void testGetAllPokemon() {
        Pokemon pokemon1 = new Pokemon(1, "Bulbasaur", "bulbasaur.png");
        Pokemon pokemon2 = new Pokemon(2, "Charmander", "Charmander.png");

        List<Pokemon> expectedPokemonList = Arrays.asList(pokemon1, pokemon2);

        when(pokemonRepository.findAll()).thenReturn(expectedPokemonList);

        List<Pokemon> actualPokemonList = pokemonService.getAllPokemon();

        assertEquals(expectedPokemonList.size(), actualPokemonList.size());
        assertTrue(expectedPokemonList.containsAll(actualPokemonList));
        assertTrue(actualPokemonList.containsAll(expectedPokemonList));
    }

    @Test
    public void testGetAllPokemonPages() {
        Pokemon pokemon1 = new Pokemon(1, "Bulbasaur", "bulbasaur.png");
        Pokemon pokemon2 = new Pokemon(2, "Charmander", "Charmander.png");

        List<Pokemon> pokemonList = Arrays.asList(pokemon1, pokemon2);
        PageRequest pageRequest = PageRequest.of(1, 1);
        Page<Pokemon> expectedPokemonPage = new PageImpl<>(pokemonList, pageRequest, pokemonList.size());


        when(pokemonRepository.findAll(pageRequest)).thenReturn(expectedPokemonPage);

        Page<Pokemon> actualPokemonPage = pokemonService.getAllPokemonPages(pageRequest);

        assertEquals(expectedPokemonPage.getTotalElements(), actualPokemonPage.getTotalElements());
        assertEquals(expectedPokemonPage.getContent(), actualPokemonPage.getContent());
    }
}