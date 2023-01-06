package com.example.mainservice.web;

import com.example.mainservice.entity.enums.MailType;
import com.example.mainservice.payload.request.mailMicroservice.MailRequest;
import com.example.mainservice.payload.request.parserMicroservice.ParserRequest;
import com.example.mainservice.payload.request.parserMicroservice.TelegramParserRequest;
import com.example.mainservice.payload.response.mailMicroservice.MailResponse;
import com.example.mainservice.payload.response.parserMicroservice.ActualNews;
import com.example.mainservice.payload.response.parserMicroservice.ActualNewsResponse;
import com.example.mainservice.payload.response.parserMicroservice.ParserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parser")
public class ParserController {
    private final WebClient.Builder webClientBuilder;

    @GetMapping("/telegram")
    // don't work with Flask
    //@CircuitBreaker(name = "parser", fallbackMethod = "serviceIsNotResponding")
    //@TimeLimiter(name = "parser")
    //@Retry(name = "parser")
    public ResponseEntity<String> parseTelegram(){
        String s = webClientBuilder
                .build()
                .post()
                .uri("http://parser-service/parse/telegram")
                .body(Mono.just(new TelegramParserRequest("Київ", 2)),TelegramParserRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(s);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @GetMapping("/actual")
    private ResponseEntity<ActualNewsResponse> parseActualNewsByQuery(@RequestBody ParserRequest parserRequest){
        ActualNewsResponse actualNewsResponse = webClientBuilder
                .build()
                .post()
                .uri("http://parser-service/parse/actual")
                .retrieve()
                .bodyToMono(ActualNewsResponse.class)
                .block();
        return ResponseEntity.ok(actualNewsResponse);
    }


    @GetMapping("/testGet")
    public void testGet(){
        String b = webClientBuilder
                .build()
                .get()
                .uri("http://mail-service/api/v2/mail/send")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(b);
        System.out.println("main-service sendGet");
    }

    @GetMapping("/testPython")
    public void testPythonService(){
        String pythonResponse = webClientBuilder
                .build()
                .post()
                .uri("http://localhost:8050/parse/twitter")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(new MailRequest("User","testspringwebapp@gmail.com"
                        , "Test",MailType.NEW_NEWS, "QQQ", "qqqq")),MailRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(pythonResponse);
    }

    @GetMapping("/testPython/get")
    public void testPythonServiceGET(){
        /*HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        restTemplate.exchange("http://localhost:8050/parse/twitter", HttpMethod.GET,entity,String.class);*/
        String b = webClientBuilder
                .build()
                .get()
                .uri("http://parser-service/parse/twitter")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(b);
        System.out.println("parser-service sendGet");
    }


    /*@GetMapping("/testPython/post")
    public void testPythonServicePost(){
        TestJson b = webClientBuilder
                .build()
                .post()
                .uri("http://parser-service/calculateGrades")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(new TestJson("User","Email", 20L)),TestJson.class)
                .retrieve()
                .bodyToMono(TestJson.class)
                .block();
        System.out.println(b);
        System.out.println("main-service send");
    }*/


    //CompletableFuture
    //CompletableFuture.supplyAsync(() ->)
    public void serviceIsNotResponding(){
        System.out.println("serviceIsNotResponding, Oups... something going wrong");
    }


}
