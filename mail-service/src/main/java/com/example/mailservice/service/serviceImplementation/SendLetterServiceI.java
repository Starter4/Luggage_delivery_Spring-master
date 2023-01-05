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

    private final MailServiceI mailService;

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

    public void sendLatterWithToken(MailRequest mailRequest) throws MessagingException {
        String title = mailRequest.getTitle();
        String email = mailRequest.getEmail();
        String username = mailRequest.getUsername();
        String token = mailRequest.getToken();
        String emailText = "Mr/Mrs "+ username+".\n"+ "Please confirm you registration(tap to link):\n"+token;
        try {
            mailService.sendSimpleMessage(email,title,emailText);
        }catch (Exception e){
            throw new MessagingException();
        }

    }
}
