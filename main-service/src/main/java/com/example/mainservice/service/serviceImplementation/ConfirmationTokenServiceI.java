package com.example.mainservice.service.serviceImplementation;

import com.example.mainservice.entity.ConfirmationToken;
import com.example.mainservice.repository.ConfirmationTokenRepository;
import com.example.mainservice.service.serviceInterface.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceI implements ConfirmationTokenService {
    private final ConfirmationTokenRepository tokenRepository;

    @Override
    public ConfirmationToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public List<ConfirmationToken> findAllTokens() {
        return tokenRepository.findAll();
    }

    @Override
    public void saveToken(ConfirmationToken token) {
        tokenRepository.save(token);
    }

    @Override
    public void updateToken(ConfirmationToken token) {
        tokenRepository.save(token);
    }

    @Override
    @Transactional
    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }

}
