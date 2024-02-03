package com.dkwasniewski.aprimons.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserDTO {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
