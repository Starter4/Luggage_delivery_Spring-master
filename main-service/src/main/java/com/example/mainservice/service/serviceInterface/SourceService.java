package com.example.mainservice.service.serviceInterface;

import com.example.mainservice.dto.SourceDTO;
import com.example.mainservice.entity.Source;

import java.util.List;

public interface SourceService {
    void addNewSource(Source source);
    SourceDTO getSourceById(long id);
    SourceDTO getSourcesByName(String sourceName);
    List<SourceDTO> getAllSourcesByStatus(boolean sourceStatus);
    void updateSource(Source source);
    void deleteSourceById(long id);
    void deleteSourceByName(String sourceName);
}
