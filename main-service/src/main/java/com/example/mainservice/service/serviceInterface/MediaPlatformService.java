package com.example.mainservice.service.serviceInterface;

import com.example.mainservice.entity.MediaPlatform;

import java.util.List;

public interface MediaPlatformService {
    void addNewMediaPlatform(MediaPlatform mediaPlatform);
    MediaPlatform getById(long id);
    List<MediaPlatform> getAll();
    List<MediaPlatform> getAllByPlatformName(String mediaPlatformName);
    void updateMediaPlatform(MediaPlatform mediaPlatform);
    void deleteMediaPlatformById(long id);
}
