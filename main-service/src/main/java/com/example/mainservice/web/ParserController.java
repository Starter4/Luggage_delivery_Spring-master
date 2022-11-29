package com.example.mainservice.web;

import com.example.mainservice.payload.request.parserMicroservice.ParserRequest;
import com.example.mainservice.payload.response.parserMicroservice.ParserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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


}
