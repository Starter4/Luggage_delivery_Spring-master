package com.example.mainservice.facade;

import com.example.mainservice.dto.MediaPlatformDTO;
import com.example.mainservice.entity.MediaPlatform;
import org.springframework.stereotype.Component;

@Component
public class MediaPlatformFacade {
    public MediaPlatformDTO convertPlatformToPlatformDTO(MediaPlatform platform) {
        return MediaPlatformDTO.builder()
//                .id(platform.getId())
                .created(platform.getCreated())
                .updated(platform.getUpdated())
                .createdBy(platform.getCreatedBy())
                .lastModifiedBy(platform.getLastModifiedBy())
                .platformName(platform.getMediaPlatform())
                .active(platform.isActive())
                .build();
    }
}
