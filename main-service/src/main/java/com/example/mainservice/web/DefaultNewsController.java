package com.example.mainservice.web;


import com.example.mainservice.dto.DefaultNewsDTO;
import com.example.mainservice.entity.DefaultNews;
import com.example.mainservice.payload.request.parserMicroservice.ParserRequest;
import com.example.mainservice.payload.request.parserMicroservice.TelegramParserRequest;
import com.example.mainservice.payload.response.parserMicroservice.ActualNewsResponse;
import com.example.mainservice.service.serviceInterface.DefaultNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@EnableScheduling
@RequestMapping("/api/v1/default-news")
public class DefaultNewsController {

    private final DefaultNewsService newsService;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public DefaultNewsController(DefaultNewsService newsService, WebClient.Builder webClientBuilder) {
        this.newsService = newsService;
        this.webClientBuilder = webClientBuilder;
    }

    @Scheduled(fixedRate = 1_800_000)
    @GetMapping("/updateNews")
    public void getAndSaveActualNews() {
        ActualNewsResponse actualNewsResponse = webClientBuilder
                .build()
                .post()
                .uri("http://parser-service/parse/actual")
                .retrieve()
                .bodyToMono(ActualNewsResponse.class)
                .block();
        //System.out.println(actualNewsResponse);
        newsService.addDefaultNews(actualNewsResponse);
    }

    @GetMapping("/actual")
    public ResponseEntity<Page<DefaultNewsDTO>> getAllActualNews(Pageable pageable) {
        return ResponseEntity.ok(newsService.findAllDefaultNewsDTO(pageable));
    }

    @GetMapping("/byQuery")
    public ResponseEntity<Page<DefaultNewsDTO>> getAllActualNewsByQuery(@RequestBody ParserRequest parserRequest) {
        ActualNewsResponse actualNewsResponse = webClientBuilder
                .build()
                .post()
                .uri("http://parser-service/parse/byQuery")
                .body(Mono.just(parserRequest),ParserRequest.class)
                .retrieve()
                .bodyToMono(ActualNewsResponse.class)
                .block();
        return null;
    }

    @GetMapping("/get/{title}")
    public List<DefaultNews> getNewsByTitle(@PathVariable String title) {
        return newsService.getDefaultNewsByTitle(title);
    }



    @PutMapping("/update")
    public ResponseEntity<?> updateNews(@ModelAttribute DefaultNews news) {
        newsService.updateDefaultNews(news);
        return ResponseEntity.ok(news);
    }

    @DeleteMapping("/delete/id/{newsId}")
    public ResponseEntity<?> deleteNewsById(@PathVariable long newsId) {
        newsService.deleteNewsById(newsId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/title/{title}")
    public ResponseEntity<?> deleteNewsByTitle(@PathVariable String title) {
        newsService.deleteByTitle(title);
        return ResponseEntity.ok().build();
    }
}