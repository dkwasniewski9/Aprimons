package com.dkwasniewski.aprimons.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AllPokemonDTO {
    @Min(0)
    int page;
    @Min(20)
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
