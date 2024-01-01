package com.dkwasniewski.aprimons.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pokeballs")
@Data
public class Pokeball {
    @Id
    private String id;

    private String name;
    private String image;

    public Pokeball(String name, String image) {
        this.name = name;
        this.image = image;
    }


}
