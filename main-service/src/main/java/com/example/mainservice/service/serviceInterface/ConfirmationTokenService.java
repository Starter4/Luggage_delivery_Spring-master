package com.example.mainservice.service.serviceInterface;

import com.example.mainservice.entity.ConfirmationToken;

public interface ConfirmationTokenService {
    ConfirmationToken findByToken(String token);
    void saveToken(ConfirmationToken token);
    //int updateConfirmedAt(String token);
    void deleteToken(String token);
}
