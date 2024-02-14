package com.dkwasniewski.aprimons.exceptions;

import com.dkwasniewski.aprimons.model.User;
import lombok.Getter;

@Getter
public class NotActivatedException extends RuntimeException{
    private final User user;
    public NotActivatedException(String message, User user){
        super(message);
        this.user = user;
    }

}
