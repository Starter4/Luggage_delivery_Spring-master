package com.example.mainservice.service.serviceInterface;

import com.example.mainservice.entity.Source;

import java.util.List;

public interface SourceService {
    void addNewSource(Source source);
    Source getSourceById(long id);
    List<Source> getAllSourcesByName(String sourceName);
    List<Source> getAllSourcesByStatus(boolean sourceStatus);
    void updateSource(Source source);
    void deleteSourceById(long id);
    void deleteSourceByName(String sourceName);
}
