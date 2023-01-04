package com.example.mainservice.service.serviceImplementation;

import com.example.mainservice.entity.ConfirmationToken;
import com.example.mainservice.entity.User;
import com.example.mainservice.service.serviceInterface.RegistrationService;
import com.example.mainservice.web.MailController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationServiceI implements RegistrationService {
    private final UserServiceI userServiceI;
    private final ConfirmationTokenServiceI tokenServiceI;
    private final MailController mailController;


    /*@Transactional
    public void confirmToken(String token){
        ConfirmationToken confirmationToken = tokenServiceI.findByToken(token);

        if(confirmationToken == null){
            throw new IllegalStateException("token not found");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            userServiceI.deleteUserById(confirmationToken.getUser().getId());
            tokenServiceI.deleteToken(token);
        }

        User user = confirmationToken.getUser();
        user.setAccountVerified(true);
        userServiceI.save(user);
        tokenServiceI.deleteToken(token);;
    }*/
}
