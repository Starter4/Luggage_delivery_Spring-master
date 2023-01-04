package com.example.mainservice.web;

import com.example.mainservice.entity.ConfirmationToken;
import com.example.mainservice.service.serviceInterface.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/confirm-token")
public class ConfirmationTokenController {

    private final ConfirmationTokenService tokenService;

    @Autowired
    public ConfirmationTokenController(ConfirmationTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToken(@ModelAttribute ConfirmationToken token) {
        tokenService.saveToken(token);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/get/{token}")
    public ConfirmationToken getToken(@PathVariable String token) {
        return tokenService.findByToken(token);
    }

    @GetMapping("/get")
    public List<ConfirmationToken> getAllTokens() {
        return tokenService.findAllTokens();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateToken(@ModelAttribute ConfirmationToken token) {
        tokenService.updateToken(token);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/delete/{token}")
    public ResponseEntity<?> deleteToken(@PathVariable String token) {
        tokenService.deleteToken(token);
        return ResponseEntity.ok().build();
    }
}
