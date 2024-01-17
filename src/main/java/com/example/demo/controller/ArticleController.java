package com.example.demo.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/get/all")
    public List<Article> getArticleAll() {
        try {
            return articleRepository.findAll();
        } catch (Exception e) {
            // Log the exception
            logger.error("An error occurred", e);
            // Send an error response
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @GetMapping("/get/{id}")
    public Article getArticleById(@PathVariable Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.orElse(null); // 如果找不到对应ID的文章，可以返回null或者抛出异常
    }

    @PostMapping("/create")
    public ResponseEntity<String> createArticle(@RequestBody Article newArticle) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Article savedArticle = articleRepository.save(newArticle);
            String responseBody = objectMapper.writeValueAsString(savedArticle);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateArticle(@RequestBody Article updatedArticle) {
        try {
            if (articleRepository.existsById(updatedArticle.getId())) {
                Article existingArticle = articleRepository.findById(updatedArticle.getId()).orElse(null);
                if (existingArticle != null) {
                    if (updatedArticle.getUpdatedAt() == null) {
                        updatedArticle.setUpdatedAt(Instant.now());
                    }
                    Instant createdAt = Instant.ofEpochMilli(existingArticle.getCreatedAt());
                    updatedArticle.setCreatedAt(createdAt);
                    Article savedArticle = articleRepository.save(updatedArticle);
                    ObjectMapper objectMapper = new ObjectMapper();
                    String responseBody = objectMapper.writeValueAsString(savedArticle);
                    return ResponseEntity.ok(responseBody);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}