package com.dkwasniewski.aprimons.dto;

import lombok.Data;

@Data
public class AllPokemonDTO {
    int page;
    int size;
    String order;
    String direction;

    public AllPokemonDTO() {
        page = 0;
        size = 20;
        order = "dexNumber";
        direction = "asc";
    }
}
