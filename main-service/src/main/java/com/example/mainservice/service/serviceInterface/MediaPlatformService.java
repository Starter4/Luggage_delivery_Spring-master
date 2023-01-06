package com.example.mainservice.service.serviceInterface;

import com.example.mainservice.dto.MediaPlatformDTO;
import com.example.mainservice.entity.MediaPlatform;

import java.util.List;

public interface MediaPlatformService {
    void addNewMediaPlatform(MediaPlatform mediaPlatform);
    MediaPlatformDTO getById(long id);
    List<MediaPlatformDTO> getAll();
    MediaPlatformDTO getAllByPlatformName(String mediaPlatformName);
    void updateMediaPlatform(MediaPlatform mediaPlatform);
    void deleteMediaPlatformById(long id);
}
