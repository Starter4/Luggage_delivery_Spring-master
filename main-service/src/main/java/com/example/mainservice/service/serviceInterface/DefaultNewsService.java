package com.example.mainservice.service.serviceInterface;

import com.example.mainservice.entity.DefaultNews;

import java.util.List;

public interface DefaultNewsService {
    void addDefaultNews(DefaultNews newsDTO);
    List<DefaultNews> getAllDefaultNews();
    List<DefaultNews> getDefaultNewsByTitle(String titleName);
    DefaultNews getNewsById(long id);
    void updateDefaultNews(DefaultNews defaultNews);
    void deleteNewsById(long id);
    void deleteByTitle(String titleName);
}
