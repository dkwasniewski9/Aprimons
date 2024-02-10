package com.dkwasniewski.aprimons.dto;

import com.dkwasniewski.aprimons.model.Pokeball;
import com.dkwasniewski.aprimons.model.Pokemon;
import com.dkwasniewski.aprimons.model.UserCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PokemonCollectionDTO {
    private String username;
    private String userId;
    private boolean editable;
    private List<Pokemon> pokemonList;
    private List<Pokeball> pokeballList;
    private UserCollection userCollection;
}
