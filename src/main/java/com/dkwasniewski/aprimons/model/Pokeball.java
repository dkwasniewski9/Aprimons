package com.dkwasniewski.aprimons.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pokeballs")
@Data
@NoArgsConstructor
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
