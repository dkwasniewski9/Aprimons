package com.dkwasniewski.aprimons.controller;

import com.dkwasniewski.aprimons.dto.PokemonCollectionDTO;
import com.dkwasniewski.aprimons.dto.SaveCollectionDTO;
import com.dkwasniewski.aprimons.model.*;
import com.dkwasniewski.aprimons.service.PokeballService;
import com.dkwasniewski.aprimons.service.PokemonService;
import com.dkwasniewski.aprimons.service.UserCollectionService;
import com.dkwasniewski.aprimons.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import util.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Controller
@RequestMapping("/collection")
public class CollectionController {
    private UserCollectionService userCollectionService;
    private UserService userService;
    private PokeballService pokeballService;
    private PokemonService pokemonService;
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public String myCollection(Model model){
        User user = userService.getCurrentUser();
        String username = user.getUsername();
        String userId = user.getId();
        boolean editable = true;
        UserCollection userCollection = userCollectionService.getUserCollection(user.getId());
        List<Pokemon> pokemonList = pokemonService.getAllPokemon();
        List<Pokeball> pokeballList = pokeballService.getAllPokeball();
        model.addAttribute("DTO", new PokemonCollectionDTO(username, userId, editable, pokemonList, pokeballList, userCollection));
        return "collection";
    }

    @GetMapping()
    public String collection(@RequestParam(required = false) String username, Model model){
        if(username == null){
            return "redirect:/collection/my";
        }
        User user = userService.findUserByUsername(username);
        if (user == null) {
            //TODO obsluga bledu
            return "redirect:/error";
        }
        String userId = user.getId();
        boolean editable = username.equals(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("DTO", new PokemonCollectionDTO(username, userId, editable,
                                                                        pokemonService.getAllPokemon(),
                                                                        pokeballService.getAllPokeball(),
                                                                        userCollectionService.getUserCollection(user.getId())));

        return "collection";
    }
    @PostMapping("save")
    @PreAuthorize("isAuthenticated()")
    public String saveCollection(SaveCollectionDTO saveCollectionDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "redirect:/error";
        }
        if (saveCollectionDTO.getUserId() == null || saveCollectionDTO.getSelectedIds() == null) {
            throw new IllegalArgumentException("Nieprawidłowe dane wejściowe");
        }
        UserCollection userCollection = userCollectionService.getUserCollection(saveCollectionDTO.getUserId());
        if (userCollection == null) {
            throw new RuntimeException("Kolekcja użytkownika nie istnieje");
        }
        List<OwnedPokemon> newCollection = new ArrayList<>();
        for (String recordId : saveCollectionDTO.getSelectedIds()) {
            String[] parts = recordId.split("-");
            String pokemonId = parts[0];
            String ballId = parts[1];
            boolean foundRecord = false;
            for(OwnedPokemon pokemon : newCollection){
                if(pokemon.getPokemonId().equals(pokemonId)){
                    pokemon.getPokeballVariantIds().add(ballId);
                    foundRecord = true;
                }
            }
            if(!foundRecord){
                List<String> balls = new ArrayList<>();
                balls.add(ballId);
                newCollection.add(new OwnedPokemon(pokemonId, balls));
            }
        }
        userCollection.setOwnedPokemonList(newCollection);
        userCollectionService.saveUserCollection(userCollection);
        return "redirect:/collection/my";
    }
}
