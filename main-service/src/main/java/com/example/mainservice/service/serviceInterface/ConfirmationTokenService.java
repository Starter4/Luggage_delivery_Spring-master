package com.example.mainservice.service.serviceInterface;

import com.example.mainservice.entity.ConfirmationToken;

import java.util.List;

public interface ConfirmationTokenService {
    ConfirmationToken findByToken(String token);
    List<ConfirmationToken> findAllTokens();
    void saveToken(ConfirmationToken token);
    void updateToken(ConfirmationToken token);
    //int updateConfirmedAt(String token);
    void deleteToken(String token);
}
