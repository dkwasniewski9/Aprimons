package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.Pokeball;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PokeballServiceTest {
    @Mock
    private PokeballService pokeballService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testGetAllPokeball() {
        Pokeball pokeball1 = new Pokeball();
        pokeball1.setId("1");
        pokeball1.setName("Safari");

        Pokeball pokeball2 = new Pokeball();
        pokeball2.setId("2");
        pokeball2.setName("Dream");

        List<Pokeball> expectedPokeballs = Arrays.asList(pokeball1, pokeball2);

        when(pokeballService.getAllPokeball()).thenReturn(expectedPokeballs);

        List<Pokeball> actualPokeballs = pokeballService.getAllPokeball();

        assertEquals(expectedPokeballs.size(), actualPokeballs.size());
        assertTrue(expectedPokeballs.containsAll(actualPokeballs));
        assertTrue(actualPokeballs.containsAll(expectedPokeballs));
    }

    @Test
    public void testGetAllPokeballEmpty() {
        when(pokeballService.getAllPokeball()).thenReturn(Collections.emptyList());

        List<Pokeball> actualPokeballs = pokeballService.getAllPokeball();

        assertTrue(actualPokeballs.isEmpty());
    }
}