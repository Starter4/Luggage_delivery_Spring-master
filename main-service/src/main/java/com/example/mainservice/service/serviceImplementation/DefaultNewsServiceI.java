package com.example.mainservice.service.serviceImplementation;

import com.example.mainservice.dto.DefaultNewsDTO;
import com.example.mainservice.entity.DefaultNews;
import com.example.mainservice.entity.Source;
import com.example.mainservice.facade.DefaultNewsFacade;
import com.example.mainservice.payload.response.parserMicroservice.ActualNews;
import com.example.mainservice.payload.response.parserMicroservice.ActualNewsResponse;
import com.example.mainservice.repository.DefaultNewsRepository;
import com.example.mainservice.repository.MediaPlatformRepository;
import com.example.mainservice.repository.SourceRepository;
import com.example.mainservice.service.serviceInterface.DefaultNewsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

//TODO create method to transfer DefaultNews -> DefaultNewDTO

@Service
@Transactional
public class DefaultNewsServiceI implements DefaultNewsService {

    private final DefaultNewsRepository defaultNewsRepository;
    private final SourceRepository sourceRepository;
    private final MediaPlatformRepository mediaRepository;
    private final DefaultNewsFacade newsFacade;

    public DefaultNewsServiceI(DefaultNewsRepository defaultNewsRepository, SourceRepository sourceRepository, MediaPlatformRepository mediaRepository, DefaultNewsFacade newsFacade) {
        this.defaultNewsRepository = defaultNewsRepository;
        this.sourceRepository = sourceRepository;
        this.mediaRepository = mediaRepository;
        this.newsFacade = newsFacade;
    }

    @Override
    public void addDefaultNews(ActualNewsResponse actualNewsResponse) {
        //?
        /*getAllDefaultNews().stream()
                .map(news -> news.setActive(false)).forEach(defaultNewsRepository::save);*/
        actualNewsResponse.getActualNews().stream()
                .map(this::responseToDefaultNews)
                .forEach(defaultNewsRepository::save);
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
    public Page<DefaultNewsDTO> findAllDefaultNewsDTO(Pageable pageable) {
        Page<DefaultNews> entityDTO = defaultNewsRepository.findAll(pageable);
        return entityDTO.map(newsFacade::convertNewsToNewsDTO);
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

    private DefaultNews responseToDefaultNews(ActualNews actualNews){
        DefaultNews defaultNews = new DefaultNews();
        defaultNews.setActive(true);
        defaultNews.setTitle(actualNews.getNewsTitle());
        defaultNews.setNewsInfo(actualNews.getNewsText());
        defaultNews.setLink(actualNews.getNewsLink());
        defaultNews.setImageLink(actualNews.getImageLink());
        defaultNews.setPublishedDate(new Date(actualNews.getPublishedDate()));
        defaultNews.setCreated(new Timestamp(new Date().getTime()));
        if(sourceRepository.findBySourceName(actualNews.getNewsResource()) == null){
            Source source = new Source();
            source.setCreated(new Timestamp(new Date().getTime()));
            source.setSourceName(actualNews.getNewsResource());
            source.setActive(true);
            source.setMediaPlatform(mediaRepository.findByMediaPlatform(actualNews.getNewsResource()));
            sourceRepository.save(source);
        }
        defaultNews.setSource(sourceRepository.findBySourceName(actualNews.getNewsResource()));
        return defaultNews;
    }
}
