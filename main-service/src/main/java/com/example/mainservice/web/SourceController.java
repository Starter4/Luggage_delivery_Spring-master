package com.example.mainservice.web;

import com.example.mainservice.dto.SourceDTO;
import com.example.mainservice.entity.Source;
import com.example.mainservice.service.serviceInterface.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/source")
public class SourceController {

    private final SourceService sourceService;

    @Autowired
    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewSource(@ModelAttribute Source source) {
        sourceService.addNewSource(source);
        return ResponseEntity.ok(source);
    }

    @GetMapping("/get/id/{sourceId}")
    public SourceDTO getSourceById(@PathVariable long sourceId) {
        return sourceService.getSourceById(sourceId);
    }

    @GetMapping("/get/name/{sourceName}")
    public SourceDTO getSourcesByName(@PathVariable String sourceName) {
        return sourceService.getSourcesByName(sourceName);
    }

    @GetMapping("/get/status/{sourceStatus}")
    public List<SourceDTO> getSourcesByStatus(@PathVariable boolean sourceStatus) {
        return sourceService.getAllSourcesByStatus(sourceStatus);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSource(@ModelAttribute Source source) {
        sourceService.updateSource(source);
        return ResponseEntity.ok(source);
    }

    @DeleteMapping("/delete/id/{sourceId}")
    public ResponseEntity<?> deleteSourceById(@PathVariable long sourceId) {
        sourceService.deleteSourceById(sourceId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/name/{sourceName}")
    public ResponseEntity<?> deleteSourcesByName(@PathVariable String sourceName) {
        sourceService.deleteSourceByName(sourceName);
        return ResponseEntity.ok().build();
    }
}