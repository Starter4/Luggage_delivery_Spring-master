package com.example.mainservice.web;

import com.example.mainservice.entity.enums.MailType;
import com.example.mainservice.payload.request.mailMicroservice.MailRequest;
import com.example.mainservice.payload.response.mailMicroservice.MailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailController {
    private final WebClient.Builder webClientBuilder;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody MailRequest mailRequest){
        //new MailRequest("User","testspringwebapp@gmail.com"
        //                        , "Test", MailType.NEW_NEWS, "QQQ", "qqqq")
        MailResponse mailResponse = webClientBuilder
                .build()
                .post()
                .uri("http://mail-service/api/v2/mail/send")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(mailRequest),MailRequest.class)
                .retrieve()
                .bodyToMono(MailResponse.class)
                .block();
        if (mailResponse != null) {
            System.out.println(mailResponse.getMailStatus().name());
        }else {
            System.out.println("null");
        }
        System.out.println("main-service send");
        return null;
    }



}
