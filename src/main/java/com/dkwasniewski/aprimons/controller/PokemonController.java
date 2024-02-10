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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public String AllPokemons(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size,
                              @RequestParam(required = false, defaultValue = "dexNum") String order,
                              @RequestParam(required = false, defaultValue = "asc") String direction, Model model) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), order);
        Page<Pokemon> pokemons = pokemonService.getAllPokemonPages(pageRequest);
        model.addAttribute("pokemons", pokemons);
        List<Pokeball> pokeballs = pokeballService.getAllPokeball();
        model.addAttribute(pokeballs);
        model.addAttribute("order", order);
        model.addAttribute("direction", direction);
        return "pokemons";
    }
}
