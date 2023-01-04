package com.example.mainservice.repository;

import com.example.mainservice.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {

    ConfirmationToken findByToken(String token);
    List<ConfirmationToken> findAll();
    void deleteByToken(String token);
}
