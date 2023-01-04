package com.example.mainservice.payload.request.registration;

import com.example.mainservice.validations.annotations.PasswordMatches;
import com.example.mainservice.validations.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@PasswordMatches
public class SignupRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    //@Pattern(regexp = "^([A-Za-z0-9_-]+\\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Please enter your name")
    private String firstname;
    @NotEmpty(message = "Please enter your lastname")
    private String lastname;
    @NotEmpty(message = "Please enter your username")
    private String username;
    @NotEmpty(message = "Password is required")
    @Size(min = 8)
    private String password;
    private String confirmPassword;


}
