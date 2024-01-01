package com.dkwasniewski.aprimons.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "pokemons")
@Data
public class Pokemon {
    @Id
    private String id;

    private int dexNumber;
    private String name;
    private String image;

    @DBRef
    private List<Pokeball> pokeballs;
    public Pokemon(int dexNumber, String name, String image) {
        this.dexNumber = dexNumber;
        this.name = name;
        this.image = image;
    }

    public Pokemon(String name) {
        this.name = name;
    }
}
