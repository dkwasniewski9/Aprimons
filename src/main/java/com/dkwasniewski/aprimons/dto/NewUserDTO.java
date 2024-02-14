package com.dkwasniewski.aprimons.dto;

import com.dkwasniewski.aprimons.validator.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@PasswordMatch
public class NewUserDTO {
    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
}
