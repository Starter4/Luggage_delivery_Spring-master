package com.example.mainservice.payload.response.authentication;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
    private String username;
    private String password;

    public InvalidLoginResponse(){
        this.username = "Invalid username";
        this.password = "invalid password";
    }
}
