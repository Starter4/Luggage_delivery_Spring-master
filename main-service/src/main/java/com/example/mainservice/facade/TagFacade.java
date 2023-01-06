package com.example.mainservice.facade;

import com.example.mainservice.dto.TagDTO;
import com.example.mainservice.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TagFacade {

    private final DefaultNewsFacade newsFacade;
    private final UserFacade userFacade;

    @Autowired
    public TagFacade(DefaultNewsFacade newsFacade, UserFacade userFacade) {
        this.newsFacade = newsFacade;
        this.userFacade = userFacade;
    }

    public TagDTO convertTagToDTO(Tag tag) {
        return TagDTO.builder()
                .tagName(tag.getTagName())
                .enable(tag.isActive())
                .userDTOSet(tag.getUserTagSet().stream()
                        .map(userFacade::convertUserToDTO)
                        .collect(Collectors.toSet())
                )
                .defaultNewsDTOSet(tag.getDefaultNewsSet().stream().
                        map(newsFacade::convertNewsToNewsDTO)
                        .collect(Collectors.toSet())
                )
                .build();
    }
}
