package com.example.mainservice.facade;

import com.example.mainservice.dto.MediaPlatformDTO;
import com.example.mainservice.dto.SourceDTO;
import com.example.mainservice.entity.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SourceFacade {

    private final MediaPlatformFacade platformFacade;
    private final DefaultNewsFacade newsFacade;
    private final UserFacade userFacade;

    @Autowired
    public SourceFacade(MediaPlatformFacade platformFacade, DefaultNewsFacade newsFacade, UserFacade userFacade) {
        this.platformFacade = platformFacade;
        this.newsFacade = newsFacade;
        this.userFacade = userFacade;
    }

    public SourceDTO convertSourceToDTO(Source source) {
        return SourceDTO.builder()
//                .id(source.getId())
                .created(source.getCreated())
                .sourceName(source.getSourceName())
                .enable(source.isActive())
                .mediaPlatformDTO(
                    platformFacade.convertPlatformToPlatformDTO(source.getMediaPlatform()))
                .defaultNewsDTOSet(
                        source.getDefaultNewsSet().stream()
                                .map(newsFacade::convertNewsToNewsDTO)
                                .collect(Collectors.toSet())
                )
                .userDTOSet(
                        source.getUserSourceSet().stream()
                                .map(userFacade::convertUserToDTO)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
