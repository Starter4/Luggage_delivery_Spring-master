package com.example.mainservice.service.serviceImplementation;

import com.example.mainservice.entity.MediaPlatform;
import com.example.mainservice.repository.MediaPlatformRepository;
import com.example.mainservice.service.serviceInterface.MediaPlatformService;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO create method to transfer MediaPlatform -> MediaPlatformDTO

@Service
public class MediaPlatformServiceI implements MediaPlatformService {

    private final MediaPlatformRepository mediaPlatformRepository;

    public MediaPlatformServiceI(MediaPlatformRepository mediaPlatformRepository) {
        this.mediaPlatformRepository = mediaPlatformRepository;
    }

    @Override
    public void addNewMediaPlatform(MediaPlatform mediaPlatform) {
        mediaPlatformRepository.save(mediaPlatform);
    }

    @Override
    public MediaPlatform getById(long id) {
        return mediaPlatformRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Media platform with id %d is not found", id)));
    }

    @Override
    public List<MediaPlatform> getAll() {
        return mediaPlatformRepository.findAll();
    }

    @Override
    public List<MediaPlatform> getAllByPlatformName(String mediaPlatformName) {
        return mediaPlatformRepository.findAllByMediaPlatform(mediaPlatformName);
    }

    @Override
    public void updateMediaPlatform(MediaPlatform mediaPlatform) {
        mediaPlatformRepository.save(mediaPlatform);
    }

    @Override
    public void deleteMediaPlatformById(long id) {
        mediaPlatformRepository.deleteById(id);
    }
}
