package com.example.mailservice.service.serviceInterfaces;

import javax.mail.MessagingException;

public interface SendLetterService {

    void sendSimpleMessage(String to, String subject, String text);
    void sendMessageWithHTML(String to, String subject, String messageToSend) throws MessagingException;
    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException ;
}
