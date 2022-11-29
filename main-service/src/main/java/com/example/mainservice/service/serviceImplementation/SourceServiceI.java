package com.example.mainservice.service.serviceImplementation;


import com.example.mainservice.entity.Source;
import com.example.mainservice.repository.SourceRepository;
import com.example.mainservice.service.serviceInterface.SourceService;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO create method to transfer Source -> SourceDTO

@Service
public class SourceServiceI implements SourceService {

    private final SourceRepository sourceRepository;

    public SourceServiceI(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    @Override
    public void addNewSource(Source source) {
        sourceRepository.save(source);
    }

    @Override
    public Source getSourceById(long id) {
        return sourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Source with id %d is not found", id)));
    }

    @Override
    public List<Source> getAllSourcesByName(String sourceName) {
        return sourceRepository.findAllBySourceName(sourceName);
    }

    @Override
    public List<Source> getAllSourcesByStatus(boolean sourceStatus) {
        return sourceRepository.findAllByActive(sourceStatus);
    }

    @Override
    public void updateSource(Source source) {
        sourceRepository.save(source);
    }

    @Override
    public void deleteSourceById(long id) {
        sourceRepository.deleteById(id);
    }

    @Override
    public void deleteSourceByName(String sourceName) {
        sourceRepository.deleteBySourceName(sourceName);
    }
}



