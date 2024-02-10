package com.dkwasniewski.aprimons.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OwnedPokemon {
    private String PokemonId;
    private List<String> pokeballVariantIds;
}
