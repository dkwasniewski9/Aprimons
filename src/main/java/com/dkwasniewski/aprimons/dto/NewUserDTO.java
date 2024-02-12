package com.dkwasniewski.aprimons.dto;

import com.dkwasniewski.aprimons.validator.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordMatch
public class NewUserDTO {
    @NotNull
    @Size(min = 6)
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;
}
