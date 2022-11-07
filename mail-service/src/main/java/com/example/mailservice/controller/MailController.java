package com.example.mailservice.controller;

import com.example.mailservice.dto.MailRequest;
import com.example.mailservice.dto.MailResponse;
import com.example.mailservice.dto.MailStatus;
import com.example.mailservice.service.serviceImplementation.SendLetterServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/mail",produces="application/json")
@CrossOrigin("http://localhost:8080")
public class MailController {

    SendLetterServiceI iSendLetterService;

    @PostMapping("/send")
    public ResponseEntity<MailResponse> sendLetter(@RequestBody MailRequest mailRequest){
        try {
            iSendLetterService.sendMassage(mailRequest);
            return new ResponseEntity<>(new MailResponse(MailStatus.SEND),HttpStatus.OK);
        } catch (MessagingException e) {
            return new ResponseEntity<>(new MailResponse(MailStatus.ERROR),HttpStatus.BAD_GATEWAY);
        }
    }


}
