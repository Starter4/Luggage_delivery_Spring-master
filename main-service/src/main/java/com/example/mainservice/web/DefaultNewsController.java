package com.example.mainservice.web;


import com.example.mainservice.entity.DefaultNews;
import com.example.mainservice.service.serviceInterface.DefaultNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/default-news")
public class DefaultNewsController {

    private final DefaultNewsService newsService;

    @Autowired
    public DefaultNewsController(DefaultNewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNews(@ModelAttribute DefaultNews news) {
        newsService.addDefaultNews(news);
        return ResponseEntity.ok(news);
    }

    @GetMapping("/get/{title}")
    public List<DefaultNews> getNewsByTitle(@PathVariable String title) {
        return newsService.getDefaultNewsByTitle(title);
    }

    @GetMapping("/get")
    public List<DefaultNews> getAllNews() {
        return newsService.getAllDefaultNews();
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