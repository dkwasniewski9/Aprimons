package com.dkwasniewski.aprimons.model;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@Getter
@RequiredArgsConstructor
public class User {
    @Id
    private String id;
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String role;
    private boolean isActive;

    public User() {
        this.isActive = false;
    }
}
