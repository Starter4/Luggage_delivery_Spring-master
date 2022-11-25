package com.example.mailservice.service.serviceImplementation;

import com.example.mailservice.dto.MailRequest;
import com.example.mailservice.letterBuilder.LetterBodyCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendLetterServiceI {

    MailServiceI mailService;

    public void sendMassage(MailRequest mailRequest) throws MessagingException {
        String title = mailRequest.getTitle();
        String email = mailRequest.getEmail();
        String letterBody = "AAA";
        /*switch (mailRequest.getMailType()){
            case REGISTRATION :
                letterBody = LetterBodyCreator.createRegistrationBody(mailRequest.getUsername(),mailRequest.getToken());
                break;
            case CHANGE_PASS :
                break;
            case SYSTEM_UPDATE :
                break;
            case NEW_NEWS :
                break;
        }*/
        mailService.sendMessageWithHTML(email,title,letterBody);
    }
}
