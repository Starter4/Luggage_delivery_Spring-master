package com.example.mailservice.service.serviceImplementation;

import com.example.mailservice.service.serviceInterfaces.SendLetterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@Slf4j
public class MailServiceI implements SendLetterService {

    //@Value("${mail.address}")
    private static final String MAIL_ADDRESS = "testspringwebapp@gmail.com";

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailServiceI(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        log.debug("In the sendSimpleMessage method");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MAIL_ADDRESS);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
        log.info("Message send...");
        log.debug("End of sendSimpleMessage method");
    }

    public void sendMessageWithHTML(String to, String subject, String messageToSend) throws MessagingException {
        log.debug("In the sendMessageWithHTML method");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            message.setContent(messageToSend,"text/html; charset=utf-8");
            helper.setFrom(MAIL_ADDRESS);
            helper.setTo(to);
            helper.setSubject(subject);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("""
                    sendMessageWithAttachment was failed\s
                    setTo: {}
                    """,to);
            throw new MessagingException();
        }



        log.info("Message send...");
        log.debug("End of sendMessageWithHTML method");
    }

    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException {
        log.debug("In the sendMessageWithAttachment method");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(MAIL_ADDRESS);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file
                    = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);
        } catch (MessagingException e) {
            log.error("""
                    sendMessageWithAttachment was failed\s
                    setTo: {}
                    setText: {}
                    """,to,text);
            throw new MessagingException();
        }
        javaMailSender.send(message);
        log.info("Message send...");
        log.debug("End of sendMessageWithAttachment method");
    }

    //generate String from thymeleaf template
    private String parseThymeleafTemplate() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        //context.setVariable("selectedOrder", orderDTO);

        //return templateEngine.process("ticketPattern", context);
        return null;
    }
}

