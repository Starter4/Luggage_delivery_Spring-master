package com.example.mainservice.facade;

import com.example.mainservice.dto.DefaultNewsDTO;
import com.example.mainservice.entity.DefaultNews;
import org.springframework.stereotype.Component;

@Component
public class DefaultNewsFacade {
    public DefaultNewsDTO convertNewsToNewsDTO(DefaultNews news){
        return DefaultNewsDTO.builder()
                .title(news.getTitle())
                .newsInfo(news.getNewsInfo())
                .link(news.getLink())
                .imageLink(news.getImageLink())
                .build();
    }
}
