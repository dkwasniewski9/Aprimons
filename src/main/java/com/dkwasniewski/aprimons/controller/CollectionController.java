package com.dkwasniewski.aprimons.controller;

import com.dkwasniewski.aprimons.exceptions.NotActivatedException;
import com.dkwasniewski.aprimons.exceptions.UserNotFoundException;
import com.dkwasniewski.aprimons.dto.PokemonCollectionDTO;
import com.dkwasniewski.aprimons.dto.SaveCollectionDTO;
import com.dkwasniewski.aprimons.model.*;
import com.dkwasniewski.aprimons.service.PokeballService;
import com.dkwasniewski.aprimons.service.PokemonService;
import com.dkwasniewski.aprimons.service.UserCollectionService;
import com.dkwasniewski.aprimons.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Controller
@RequestMapping("/collection")
public class CollectionController {
    private UserCollectionService userCollectionService;
    private UserService userService;
    private PokeballService pokeballService;
    private PokemonService pokemonService;
    private MessageSource messageSource;
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public String myCollection(Model model, HttpServletRequest request){
        User user = userService.getCurrentUser();
        if(!user.isActive()){
            throw new NotActivatedException(messageSource.getMessage("notActivated.message", null, (Locale) request.getSession().getAttribute("locale")), user);
        }
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
    public String collection(@RequestParam(required = false) String username, Model model, HttpServletRequest request){
        if(username == null){
            return "redirect:/collection/my";
        }
        User user = userService.findUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(messageSource.getMessage("user.notFound1", null, (Locale) request.getSession().getAttribute("locale")) + username +
                    messageSource.getMessage("user.notFound2", null, (Locale) request.getSession().getAttribute("locale")));
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
    public String saveCollection(SaveCollectionDTO saveCollectionDTO, HttpServletRequest request){
        if (saveCollectionDTO.getUserId() == null || saveCollectionDTO.getSelectedIds() == null) {
            throw new IllegalArgumentException(messageSource.getMessage("request.bad", null, (Locale) request.getSession().getAttribute("locale")));
        }
        UserCollection userCollection = userCollectionService.getUserCollection(saveCollectionDTO.getUserId());
        if (userCollection == null) {
            throw new RuntimeException(messageSource.getMessage("collection.notExist", null, (Locale) request.getSession().getAttribute("locale")));
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
