package com.example.mainservice.service.serviceImplementation;

import com.example.mainservice.dto.MediaPlatformDTO;
import com.example.mainservice.entity.MediaPlatform;
import com.example.mainservice.facade.MediaPlatformFacade;
import com.example.mainservice.repository.MediaPlatformRepository;
import com.example.mainservice.service.serviceInterface.MediaPlatformService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//TODO create method to transfer MediaPlatform -> MediaPlatformDTO

@Service
public class MediaPlatformServiceI implements MediaPlatformService {

    private final MediaPlatformRepository mediaPlatformRepository;
    private final MediaPlatformFacade platformFacade;

    public MediaPlatformServiceI(MediaPlatformRepository mediaPlatformRepository,
                                 MediaPlatformFacade platformFacade) {
        this.mediaPlatformRepository = mediaPlatformRepository;
        this.platformFacade = platformFacade;
    }

    @Override
    public void addNewMediaPlatform(MediaPlatform mediaPlatform) {
        mediaPlatformRepository.save(mediaPlatform);
    }

    @Override
    public MediaPlatformDTO getById(long id) {
        return platformFacade.convertPlatformToPlatformDTO(mediaPlatformRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Media platform with id %d is not found", id))));
    }

    @Override
    public List<MediaPlatformDTO> getAll() {
        return mediaPlatformRepository.findAll().stream()
                .map(platformFacade::convertPlatformToPlatformDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MediaPlatformDTO getAllByPlatformName(String mediaPlatformName) {
        return platformFacade.convertPlatformToPlatformDTO(
                mediaPlatformRepository.findByMediaPlatform(mediaPlatformName));
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
