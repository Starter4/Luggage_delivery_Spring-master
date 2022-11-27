package com.example.mailservice.controller;

import com.example.mailservice.dto.MailRequest;
import com.example.mailservice.dto.MailResponse;
import com.example.mailservice.dto.enums.MailStatus;
import com.example.mailservice.service.serviceImplementation.SendLetterServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v2/mail")
//@CrossOrigin("http://localhost:8080")
public class MailController {

    private final SendLetterServiceI sendLetterService;

    @PostMapping("/send")
    public ResponseEntity<MailResponse> sendLetter(@RequestBody MailRequest mailRequest){
        System.out.println(mailRequest);
        System.out.println("mail-service");
        try {
            sendLetterService.sendMassage(mailRequest);
            return new ResponseEntity<>(new MailResponse(MailStatus.SEND),HttpStatus.OK);
        } catch (MessagingException e) {
            return new ResponseEntity<>(new MailResponse(MailStatus.ERROR),HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/send")
    public String sendLetterGet(){
        System.out.println("mail-service GET");
        return "OK";
    }



}
