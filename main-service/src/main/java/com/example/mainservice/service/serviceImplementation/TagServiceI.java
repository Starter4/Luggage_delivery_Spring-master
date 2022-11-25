package com.example.mainservice.service.serviceImplementation;

import com.example.mainservice.dto.TagDTO;
import com.example.mainservice.entity.Tag;
import com.example.mainservice.repository.TagRepository;
import com.example.mainservice.service.serviceInterface.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceI implements TagService {
    private final TagRepository tagRepository;

    @Override
    public Optional<Tag> findTagByTagName(String tagName) {
        return Optional.empty();
    }

    @Override
    public void addTag(TagDTO tagDTO) {

    }

    @Override
    public void updateTag(TagDTO tagDTO, Long id) {

    }

    @Override
    public Page<TagDTO> findAllTags(Pageable pageable, int pageNumber, String direction, String sort) {
        Pageable changePageable = PageRequest.of(pageNumber - 1, pageable.getPageSize()
                ,direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        Page<Tag> tagPage = tagRepository.findAll(changePageable);
        return null;
    }

    @Override
    public Page<TagDTO> findAllUserPreferTags(String login, Pageable pageable, int pageNumber, String direction, String sort) {
        Pageable changePageable = PageRequest.of(pageNumber - 1, pageable.getPageSize()
                ,direction.equals("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        Page<Tag> tagPage = tagRepository.findTagsByUserTagSet_Login(login,changePageable);
        return null;
    }

    @Override
    public void setTagValidity(String tagName) {

    }

    @Override
    public void deleteTagByTagName(String tagName) {
        tagRepository.deleteTagByTagName(tagName);
    }
}
