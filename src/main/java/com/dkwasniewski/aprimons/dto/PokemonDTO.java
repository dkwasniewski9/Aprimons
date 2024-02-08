package com.dkwasniewski.aprimons.dto;

import com.dkwasniewski.aprimons.model.Pokeball;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class PokemonDTO {
    private int dexNumber;
    private String name;
    private String image;
    private List<Pokeball> pokeballs;
}
