package com.example.mainservice.service.serviceImplementation;

import com.example.mainservice.entity.DefaultNews;
import com.example.mainservice.repository.DefaultNewsRepository;
import com.example.mainservice.service.serviceInterface.DefaultNewsService;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO create method to transfer DefaultNews -> DefaultNewDTO

@Service
public class DefaultNewsServiceI implements DefaultNewsService {

    private final DefaultNewsRepository defaultNewsRepository;

    public DefaultNewsServiceI(DefaultNewsRepository defaultNewsRepository) {
        this.defaultNewsRepository = defaultNewsRepository;
    }

    @Override
    public void addDefaultNews(DefaultNews defaultNews) {
        defaultNewsRepository.save(defaultNews);
    }

    @Override
    public List<DefaultNews> getAllDefaultNews() {
        return defaultNewsRepository.findAll();
    }

    @Override
    public List<DefaultNews> getDefaultNewsByTitle(String titleName) {
        return defaultNewsRepository.findAllByTitle(titleName);
    }

    @Override
    public DefaultNews getNewsById(long id) {
        return defaultNewsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("News with id %d is not found", id)));
    }

    @Override
    public void updateDefaultNews(DefaultNews defaultNews) {
        defaultNewsRepository.save(defaultNews);
    }

    @Override
    public void deleteNewsById(long id) {
        defaultNewsRepository.deleteById(id);
    }

    @Override
    public void deleteByTitle(String titleName) {
        defaultNewsRepository.deleteByTitle(titleName);
    }
}
