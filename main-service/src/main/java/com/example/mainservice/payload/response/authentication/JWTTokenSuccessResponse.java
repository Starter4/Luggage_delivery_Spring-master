package com.example.mainservice.payload.response.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class JWTTokenSuccessResponse {
    private boolean isSuccess;
    private String token;
}
