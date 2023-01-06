package com.example.mainservice.service.serviceImplementation;


import com.example.mainservice.dto.SourceDTO;
import com.example.mainservice.entity.Source;
import com.example.mainservice.facade.SourceFacade;
import com.example.mainservice.repository.SourceRepository;
import com.example.mainservice.service.serviceInterface.SourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//TODO create method to transfer Source -> SourceDTO

@Service
public class SourceServiceI implements SourceService {

    private final SourceRepository sourceRepository;
    private final SourceFacade sourceFacade;

    public SourceServiceI(SourceRepository sourceRepository, SourceFacade sourceFacade) {
        this.sourceRepository = sourceRepository;
        this.sourceFacade = sourceFacade;
    }

    @Override
    public void addNewSource(Source source) {
        sourceRepository.save(source);
    }

    @Override
    public SourceDTO getSourceById(long id) {
        return sourceFacade.convertSourceToDTO(sourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Source with id %d is not found", id))));
    }

    @Override
    public SourceDTO getSourcesByName(String sourceName) {
        return sourceFacade.convertSourceToDTO(sourceRepository.findBySourceName(sourceName));
    }

    @Override
    public List<SourceDTO> getAllSourcesByStatus(boolean sourceStatus) {
        return sourceRepository.findAllByActive(sourceStatus).stream()
                .map(sourceFacade::convertSourceToDTO)
                .collect(Collectors.toList());
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
    @Transactional
    public void deleteSourceByName(String sourceName) {
        sourceRepository.deleteBySourceName(sourceName);
    }
}



