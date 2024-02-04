package com.dkwasniewski.aprimons.model;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
@Getter
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private String role;
    private boolean isActive;

    public User() {
        this.isActive = false;
    }
}
