package com.dkwasniewski.aprimons.controller;

import com.dkwasniewski.aprimons.dto.NewPokemonDTO;
import com.dkwasniewski.aprimons.dto.PokeballDTO;
import com.dkwasniewski.aprimons.dto.PokemonDTO;
import com.dkwasniewski.aprimons.dto.ResponseDTO;
import com.dkwasniewski.aprimons.model.Pokeball;
import com.dkwasniewski.aprimons.model.Pokemon;
import com.dkwasniewski.aprimons.service.PokeballService;
import com.dkwasniewski.aprimons.service.PokemonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import util.HttpConstants;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;
    private final PokeballService pokeballService;

    @GetMapping("all")
    public String AllPokemons(Model model) {
        List<PokemonDTO> pokemons = pokemonService.getAllPokemon()
                .stream()
                .map(pokemon -> new PokemonDTO(pokemon.getDexNumber(), pokemon.getName(), pokemon.getImage(), pokemon.getPokeballs()))
                .toList();
        model.addAttribute(pokemons);
        List<Pokeball> pokeballs = pokeballService.getAllPokeball();
        model.addAttribute(pokeballs);
        return "pokemons";
    }
    @PostMapping("/add")
    public ResponseDTO newPokemon(@RequestBody NewPokemonDTO newPokemon){
        pokemonService.savePokemon(new Pokemon(newPokemon.getName()));
        return new ResponseDTO(HttpConstants.HTTP_CREATED);
    }
}
