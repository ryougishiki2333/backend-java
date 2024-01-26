package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.Tag;
import com.example.demo.entity.Visitor;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.VisitorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = {"http://198.144.180.142", "http://viviblog.xyz/"})
@RestController
@RequestMapping("/api/visitor")
public class VisitorController {

    private final VisitorRepository visitorRepository;

    public VisitorController(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerVisitor(@RequestBody Visitor newVisitor) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Visitor savedVisitor = visitorRepository.save(newVisitor);
            String responseBody = objectMapper.writeValueAsString(savedVisitor);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
