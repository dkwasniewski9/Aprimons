package com.dkwasniewski.aprimons.controller;

import com.dkwasniewski.aprimons.dto.NewPokemonDTO;
import com.dkwasniewski.aprimons.dto.ResponseDTO;
import com.dkwasniewski.aprimons.model.Pokemon;
import com.dkwasniewski.aprimons.service.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.HttpConstants;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test endpoint works!");
    }
    @PostMapping("/add")
    public ResponseDTO newPokemon(@RequestBody NewPokemonDTO newPokemon){
        pokemonService.savePokemon(new Pokemon(newPokemon.getName()));
        return new ResponseDTO(HttpConstants.HTTP_CREATED);
    }
}
