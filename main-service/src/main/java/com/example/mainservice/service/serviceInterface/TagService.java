package com.example.mainservice.service.serviceInterface;

import com.example.mainservice.dto.TagDTO;
import com.example.mainservice.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TagService {
    Optional<Tag> findTagByTagName(String tagName);
    void addTag(TagDTO tagDTO);
    void updateTag(TagDTO tagDTO, Long id);
    Page<TagDTO> findAllTags(Pageable pageable, int pageNumber, String direction, String sort);
    Page<TagDTO> findAllUserPreferTags(String login, Pageable pageable, int pageNumber, String direction, String sort);
    void setTagValidity(String tagName);
    void deleteTagByTagName(String tagName);
}
