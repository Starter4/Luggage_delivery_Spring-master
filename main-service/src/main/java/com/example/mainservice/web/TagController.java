package com.example.mainservice.web;

import com.example.mainservice.entity.Tag;
import com.example.mainservice.service.serviceInterface.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequestMapping("/api/v1/tag")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewTag(@ModelAttribute Tag tag) {
        tagService.addTag(tag);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/get/{tagName}")
    public Tag getByTagName(@PathVariable String tagName) {
        return tagService.getByTagName(tagName);
    }

    @GetMapping("/get")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTag(@ModelAttribute Tag tag) {
        tagService.updateTag(tag);
        return ResponseEntity.ok(tag);
    }

    @DeleteMapping("/delete/{tagName}")
    public ResponseEntity<?> deleteTag(@PathVariable String tagName) {
        tagService.deleteTagByTagName(tagName);
        return ResponseEntity.ok().build();
    }
}