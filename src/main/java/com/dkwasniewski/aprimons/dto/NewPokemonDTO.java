package com.dkwasniewski.aprimons.dto;

import lombok.Data;

@Data
public class NewPokemonDTO {
    public String name;

    public NewPokemonDTO(String name) {
        this.name = name;
    }

    public NewPokemonDTO() {
    }
}
