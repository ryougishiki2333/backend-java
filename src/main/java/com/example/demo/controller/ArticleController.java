package com.example.demo.controller;
import com.example.demo.entity.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = {"http://198.144.180.142", "http://viviblog.xyz/"})
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleRepository articleRepository;

    private final TagRepository tagRepository;

    public ArticleController(ArticleRepository articleRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
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
            Set<Tag> newTags = new HashSet<>();
            for (Tag tag : newArticle.getTags()) {
                // 查询数据库，检查标签是否已存在
                Optional<Tag> existingTag = tagRepository.findByName(tag.getName());

                if (existingTag.isPresent()) {
                    // 如果标签已存在，使用现有标签
                    newTags.add(existingTag.get());
                } else {
                    // 如果标签不存在，创建一个新标签并保存到数据库
                    Tag createdTag = tagRepository.save(tag);
                    newTags.add(createdTag);
                }
            }
            newArticle.setArticleState(1);
            newArticle.setTags(newTags);

            ObjectMapper objectMapper = new ObjectMapper();
            Article savedArticle = articleRepository.save(newArticle);
            String responseBody = objectMapper.writeValueAsString(savedArticle);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/update")
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