package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/get/all")
    public List<Article> getArticleAll() {
        return articleRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public Article getArticleById(@PathVariable Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.orElse(null); // 如果找不到对应ID的文章，可以返回null或者抛出异常
    }

    @PostMapping("/create")
    public ResponseEntity<?> createArticle(@RequestBody Article newArticle) {
        try {
            System.out.println(newArticle);
            Article savedArticle = articleRepository.save(newArticle);
            return ResponseEntity.ok(savedArticle);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}