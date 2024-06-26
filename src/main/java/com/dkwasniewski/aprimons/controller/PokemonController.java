package com.dkwasniewski.aprimons.controller;

import com.dkwasniewski.aprimons.dto.AllPokemonDTO;
import com.dkwasniewski.aprimons.model.Pokeball;
import com.dkwasniewski.aprimons.model.Pokemon;
import com.dkwasniewski.aprimons.service.PokeballService;
import com.dkwasniewski.aprimons.service.PokemonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;
    private final PokeballService pokeballService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("all")
    public String AllPokemons(AllPokemonDTO allPokemonDTO,
                              Model model) {
        PageRequest pageRequest = PageRequest.of(allPokemonDTO.getPage(), allPokemonDTO.getSize(), Sort.Direction.fromString(allPokemonDTO.getDirection()), allPokemonDTO.getOrder());
        Page<Pokemon> pokemons = pokemonService.getAllPokemonPages(pageRequest);
        model.addAttribute("pokemons", pokemons);
        List<Pokeball> pokeballs = pokeballService.getAllPokeball();
        model.addAttribute(pokeballs);
        model.addAttribute("allPokemonDTO", allPokemonDTO);
        return "pokemons";
    }
}
