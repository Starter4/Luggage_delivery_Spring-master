package com.example.mainservice.web;

import com.example.mainservice.payload.request.parserMicroservice.ParserRequest;
import com.example.mainservice.payload.request.parserMicroservice.TelegramParserRequest;
import com.example.mainservice.payload.response.parserMicroservice.ParserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parser")
public class ParserController {
    private final WebClient.Builder webClientBuilder;

    @GetMapping("/google")
    // don't work with Flask
    /*@CircuitBreaker(name = "parser", fallbackMethod = "serviceIsNotResponding")
    @TimeLimiter(name = "parser")
    @Retry(name = "parser")*/
    public void testGoogleParser(){
        ParserResponse parserResponse = webClientBuilder
                .build()
                .post()
                .uri("http://parser-service/parse/google")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(new ParserRequest("Киев")),ParserRequest.class)
                .retrieve()
                .bodyToMono(ParserResponse.class)
                .block();
        System.out.println(parserResponse);
        System.out.println("Call to parser-microservice");
    }

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


    //CompletableFuture
    //CompletableFuture.supplyAsync(() ->)
    public void serviceIsNotResponding(){
        System.out.println("serviceIsNotResponding, Oups... something going wrong");
    }


}
