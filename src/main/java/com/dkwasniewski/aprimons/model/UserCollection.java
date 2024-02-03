package com.dkwasniewski.aprimons.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user_collections")
public class UserCollection
{
    @Id
    private String id;
    @DBRef
    private User userId;

    private List<OwnedPokemon> ownedPokemonList;
}
