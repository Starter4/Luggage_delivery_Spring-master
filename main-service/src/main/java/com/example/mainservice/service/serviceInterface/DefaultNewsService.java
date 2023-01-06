package com.example.mainservice.service.serviceInterface;

import com.example.mainservice.dto.DefaultNewsDTO;
import com.example.mainservice.entity.DefaultNews;
import com.example.mainservice.payload.response.parserMicroservice.ActualNewsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DefaultNewsService {
    void addDefaultNews(ActualNewsResponse actualNewsResponse);
    List<DefaultNews> getAllDefaultNews();
    List<DefaultNews> getDefaultNewsByTitle(String titleName);
    DefaultNews getNewsById(long id);
    Page<DefaultNewsDTO> findAllDefaultNewsDTO(Pageable pageable);
    void updateDefaultNews(DefaultNews defaultNews);
    void deleteNewsById(long id);
    void deleteByTitle(String titleName);
}
