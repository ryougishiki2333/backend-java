package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.Tag;
import com.example.demo.repository.TagRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://198.144.180.142", "http://viviblog.xyz/"})
@RestController
@RequestMapping("/api/tag")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping("/get/all")
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTag(@RequestBody Tag newTag) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Tag savedTag = tagRepository.save(newTag);
            String responseBody = objectMapper.writeValueAsString(savedTag);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTag(@RequestParam Long id) {
        try {
            tagRepository.deleteById(id);
            return ResponseEntity.ok("Tag deleted successfully");
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}