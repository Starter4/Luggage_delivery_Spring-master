package com.example.mainservice.service.serviceImplementation;

import com.example.mainservice.entity.ConfirmationToken;
import com.example.mainservice.repository.ConfirmationTokenRepository;
import com.example.mainservice.service.serviceInterface.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceI implements ConfirmationTokenService {
    private final ConfirmationTokenRepository tokenRepository;

    @Override
    public ConfirmationToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void saveToken(ConfirmationToken token) {
        tokenRepository.save(token);
    }

    @Override
    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }


}
