package com.example.mainservice.web;

import com.example.mainservice.entity.enums.MailType;
import com.example.mainservice.payload.request.MailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/v1/mail/")
public class MailController {

    //private final WebClient webClient;

    @Autowired
    private WebClient.Builder webClientBuilder;

    /*@GetMapping()
    public void test(){
        webClient
                .post()
                .uri("http://localhost:8082/api/v2/mail/send")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(new MailRequest("User","testspringwebapp@gmail.com"
                        , "Test",MailType.NEW_NEWS, "QQQ", "qqqq")),MailRequest.class)
                .retrieve()
                .bodyToMono(MailRequest.class)
                .block();
        System.out.println("main-service send");
    }*/

    @GetMapping("/testGet")
    public void testGet(){
        String b = webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8082/api/v2/mail")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(b);
        System.out.println("main-service sendGet");
    }


}
