package com.example.mainservice.web;

import com.example.mainservice.entity.MediaPlatform;
import com.example.mainservice.service.serviceInterface.MediaPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/media-platform")
public class MediaPlatformController {

    private final MediaPlatformService mediaPlatformService;

    @Autowired
    public MediaPlatformController(MediaPlatformService mediaPlatformService) {
        this.mediaPlatformService = mediaPlatformService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMediaPlatform(@ModelAttribute MediaPlatform platform) {
        mediaPlatformService.addNewMediaPlatform(platform);
        return ResponseEntity.ok(platform);
    }

    @GetMapping("/get")
    public List<MediaPlatform> getAllPlatforms() {
        return mediaPlatformService.getAll();
    }

    @GetMapping("/get/id/{platformId}")
    public MediaPlatform getPlatformById(@PathVariable long platformId) {
        return mediaPlatformService.getById(platformId);
    }

    @GetMapping("/get/name/{platformName}")
    public List<MediaPlatform> getAllByPlatformName(@PathVariable String platformName) {
        return mediaPlatformService.getAllByPlatformName(platformName);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePlatform(@ModelAttribute MediaPlatform mediaPlatform) {
        mediaPlatformService.updateMediaPlatform(mediaPlatform);
        return ResponseEntity.ok(mediaPlatform);
    }

    @DeleteMapping("/delete/{platformId}")
    public ResponseEntity<?> deletePlatformById(@PathVariable long platformId) {
        mediaPlatformService.deleteMediaPlatformById(platformId);
        return ResponseEntity.ok().build();
    }
}