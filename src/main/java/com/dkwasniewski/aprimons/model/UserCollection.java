package com.dkwasniewski.aprimons.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@RequiredArgsConstructor
@Data
@Document(collection = "user_collections")
public class UserCollection {
    @Id
    private String id;
    private final String userId;

    private List<OwnedPokemon> ownedPokemonList;
}
